package com.peaslimited.shoppeas.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
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
    private WholesalerTransactionsService wholesalerTransactionService;

    @Autowired
    private TransactionsService transactionService;

    @GetMapping("/gettransactionsbyuen")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String,Object> getTransactionsByWholesaler(@RequestParam String uen, @RequestParam String status) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot>  docList = transactionService.getDocByUENAndStatus(uen, status);
        System.out.println(docList);

        ArrayList<Object> transactionList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();

        DocumentSnapshot document = null;
        for (QueryDocumentSnapshot queryDocumentSnapshot : docList) {
            if (queryDocumentSnapshot != null) {
                document = queryDocumentSnapshot;

                String uid = document.get("uid").toString();
                double total_price = (double) document.get("total_price");
                double price = (double) total_price;
                dataMap.put("tid", document.getId());
                dataMap.put("uid", uid);
                dataMap.put("total_price", price);
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
    public void checkout(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        transactionService.updateToCheckout(uid, data);
    }
//
//    public double updateOneTransactionAndStock(Map<String, Object> products, String tid) throws ExecutionException, InterruptedException {
//        Map<String, Object> updateT = new HashMap<>();
//        double totalPrice = 0;
//
//        // for each wholesaler product
//        for (int i = 0; i < products.size(); i++) {
//            Map<String, Object> productsMap = (Map<String, Object>) products.get(i);
//            String swp_id = productsMap.get("swp_id").toString();
//            int quantity = Integer.parseInt(productsMap.get("quantity").toString());
//            // ACTION: add price field to products list
//            double productPrice = getProductPrice(swp_id) * quantity;
//            productsMap.put("price", productPrice);
//            String index = Integer.toString(i);
//            products.put(index, productsMap);
//
//            // ACTION: get cart total price
//            totalPrice += productPrice;
//
//            // ACTION: update stock quantity
//            updateWProductQuant(swp_id, quantity);
//        }
//
//        // ACTION: update transaction
//        // updateT.put("tid", tid);
//        updateT.put("status", "PENDING-ACCEPTANCE");
//        updateT.put("products", products);
//        updateT.put("total_price", totalPrice);
//        transactionService.updateTransaction(tid, updateT);
//
//        return totalPrice;
//    }
//
//    public void updateWProductQuant(String swp_id, int quantity) throws ExecutionException, InterruptedException {
//        WholesalerProductDTO wholesalerProductDTO = wholesalerProductService.getBySwp_id(swp_id);
//        float oldQuantity = wholesalerProductDTO.getStock();
//
//        Map<String, Object> wholeSalerProductMap = new HashMap<>();
//        wholeSalerProductMap.put("stock", oldQuantity - quantity);
//
//        wholesalerProductService.updateWholesalerProduct(swp_id, wholeSalerProductMap);
//    }
//
//    public String getTransactionFromUIDandWName(String uid, String wholesalerName) throws ExecutionException, InterruptedException {
//        DocumentSnapshot doc = wholesalerService.getDocByWholesalerName(wholesalerName);
//        if (doc == null)
//            return "null";
//        String uen = Objects.requireNonNull(doc.get("uen")).toString();
//
//        DocumentSnapshot transactionDoc = transactionService.getDocByUENAndWName(uen, uid);
//        if (transactionDoc == null)
//            return "null";
//        return transactionDoc.getId();
//    }

}
