package com.peaslimited.shoppeas.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WholesalerTransactionsService wholesalerTransactionService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private TransactionsService transactionService;

    @Autowired
    private WholesalerProductService wholesalerProductService;

    // CONSUMER METHODS
    /**
     Adds or updates a transaction record based on ADD TO CART
     Called in CartController in the add to cart function.
     
     @param data
     @throws ExecutionException
     @throws InterruptedException
     */
    /*
     @PostMapping("/newTransaction")
     
     @PreAuthorize("hasRole('CONSUMER')")
     
     @ResponseStatus(code = HttpStatus.CREATED)
     */
    public void newTransaction(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        // input: Single "item": swp_id, quantity, uen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        String uen = data.get("uen").toString();

        TransactionsDTO transaction = transactionService.getTransactionByUID(uid, "IN-CART");

        // ACTION: transaction record exists and product is from the same wholesaler
        if (transaction != null && transaction.getUen().equals(uen)) {
            // update
            transactionService.updateTransactionProduct(data, uid, "IN-CART");
        } else if (transaction != null) { // ACTION: transaction record exists and product is from different wholesaler
            createTransactionRecord(swp_id, quantity, uid, uen);
        } else {  // ACTION: transaction record DNE
            createTransactionRecord(swp_id, quantity, uid, uen);
        }
    }

    public void createTransactionRecord(String swp_id, int quantity, String uid, String uen)
            throws ExecutionException, InterruptedException {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("quantity", quantity);
        productMap.put("swp_id", swp_id);

        ArrayList<Object> productsList = new ArrayList<>();
        productsList.add(productMap);

        // find unit price
        double price = getProductPrice(swp_id);
        if (price != 0) {
            TransactionsDTO transaction = new TransactionsDTO(
                productsList,
                "IN-CART",
                price * quantity,
                uen,
                uid
            );
            transactionService.createTransaction(transaction);
        }

    }

    public double getProductPrice(String swp_id) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProd = wholesalerProductService.getBySwp_id(swp_id);
        return wholesalerProd.getPrice();
    }

    @GetMapping("/gettransactionsbyuen")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getTransactionsByWholesaler(@RequestParam String uen, @RequestParam String status)
            throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> docList = transactionService.getDocByUENAndStatus(uen, status);

        ArrayList<Object> transactionList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();

        DocumentSnapshot document = null;
        for (QueryDocumentSnapshot queryDocumentSnapshot : docList) {
            if (queryDocumentSnapshot != null) {
                document = queryDocumentSnapshot;

                String uid = Objects.requireNonNull(document.get("uid")).toString();
                double total_price = (double) document.get("total_price");
                dataMap.put("uid", uid);
                dataMap.put("total_price", total_price);
                dataMap.put("items", transactionService.getProductListfromTransaction(document, false));
                transactionList.add(dataMap);
            }
        }
        return dataMap;
    }

    @PatchMapping("/updatetransactionstatus")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateTransactionStatus(@RequestBody Map<String, Object> data) {
        transactionService.updateTransactionStatus(data);
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void checkout(@RequestBody Map<String, Object> data)
            throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        // ACTION: get transaction IDs (tid)
        ArrayList<String> transactionList = new ArrayList<>();
        float checkoutPrice = 0;

        // ACTION: GET TRANSACTION DATA
        // convert order data to array
        ArrayList<Object> cartList = (ArrayList<Object>) data.get("cart_items");
        // for each transaction
        for (Object cart : cartList) {
            Map<String, Object> transactionMap = (Map<String, Object>) cart;
            String wholesalerName = transactionMap.get("wholesaler").toString();

            // only if the wholesaler's selected currency is MYR, will the currency api be
            // double price = Double.parseDouble(data.get("price").toString());
//
//            String preferredCurrency = data.get("currency").toString();
//            double exchangeRate = 0.0;
//            double finalPrice = 0.0;
//
//            if (preferredCurrency.equals("MYR")) {
//                exchangeRate = currencyService.exchangeRate(checkoutPrice, preferredCurrency);
//                finalPrice = checkoutPrice * exchangeRate;
//            }

            String tid = getTransactionFromUIDandWName(uid, wholesalerName);
            transactionList.add(tid);
            TransactionsDTO transaction = transactionService.findByTID(tid);

            ArrayList<Object> products = transaction.getProducts();
            String uen = transaction.getUen();

            checkoutPrice += updateOneTransactionAndStock(products, tid);
        }

        // ACTION: delete cart
        ShoppingCart cartNow = cartService.getCartByUID_NonDTO(uid);
        String cid = cartNow.getCid();
        cartService.deleteWholeCart(cid);
    }

    public float updateOneTransactionAndStock(ArrayList<Object> products, String tid)
            throws ExecutionException, InterruptedException {
        Map<String, Object> updateT = new HashMap<>();
        float totalPrice = 0;

        // for each wholesaler product
        for (int i = 0; i < products.size(); i++) {
            Map<String, Object> productsMap = (Map<String, Object>) products.get(i);
            String swpid = productsMap.get("swp_id").toString();
            int quantity = Integer.parseInt(productsMap.get("quantity").toString());
            // ACTION: add price field to products list
            double productPrice = getProductPrice(swpid) * quantity;
            productsMap.put("price", productPrice);
            products.set(i, productsMap);

            // ACTION: get cart total price
            totalPrice += productPrice;

            // ACTION: update stock quantity
            updateWProductQuant(swpid, quantity);
        }

        // ACTION: update transaction
        // updateT.put("tid", tid);
        updateT.put("status", "PENDING-ACCEPTANCE");
        updateT.put("products", products);
        updateT.put("total_price", totalPrice);
        transactionService.updateTransaction(tid, updateT);

        return totalPrice;
    }

    public void updateWProductQuant(String swpid, int quantity) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProductDTO = wholesalerProductService.getBySwp_id(swpid);
        float oldQuantity = wholesalerProductDTO.getStock();

        Map<String, Object> wholeSalerProductMap = new HashMap<>();
        wholeSalerProductMap.put("stock", oldQuantity - quantity);

        wholesalerProductService.updateWholesalerProduct(swpid, wholeSalerProductMap);
    }

    public String getTransactionFromUIDandWName(String uid, String wholesalerName)
            throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = wholesalerService.getDocByWholesalerName(wholesalerName);
        if (doc == null)
            return "null";
        String uen = Objects.requireNonNull(doc.get("uen")).toString();

        DocumentSnapshot transactionDoc = transactionService.getDocByUENAndWName(uen, uid);
        if (transactionDoc == null)
            return "null";
        return transactionDoc.getId();
    }

}
