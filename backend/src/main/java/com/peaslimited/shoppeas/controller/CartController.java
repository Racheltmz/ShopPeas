package com.peaslimited.shoppeas.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.dto.mapper.ShoppingCartMapper;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.peaslimited.shoppeas.exception.ApiExceptionHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private OrderHistoryService orderService;
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private WholesalerService wholesalerService;
    @Autowired
    private WholesalerAddressService wholesalerAddressService;
    @Autowired
    private WholesalerProductService wholesalerProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ApiExceptionHandler apiExceptionHandler;

    //get cart (DTO object)
    @GetMapping("/view")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String,Object> getCartByUID() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        ShoppingCartDTO cart =  cartService.getCartByUID(uid);

        ArrayList<String> orderList = cart.getOrders();
        ArrayList<Object> cartTransactions = new ArrayList<>();

        for (String s : orderList) {
            Map<String, Object> transactionMap = new HashMap<>();
            String tid = s;
            TransactionsDTO transaction = transactionsService.findByTID(tid);
            String uen = transaction.getUen();
            WholesalerDTO wholesaler = wholesalerService.getWholesalerUID(uen);
            String wholesalerName = wholesaler.getName();
            WholesalerAddressDTO wholesalerAddress = wholesalerAddressService.getWholesalerAddress(uen);

            Map<String, Object> productsMapOld = transaction.getProducts();
            ArrayList<Object> itemsList = new ArrayList<>();

            for (int i = 0; i< productsMapOld.size(); i++) {
                Map<String, Object> itemsMap = new HashMap<>();
                String index = Integer.toString(i);
                Map<String, Object> productsMap = (Map<String, Object>) productsMapOld.get(index);

                Long q = (Long) productsMap.get("quantity");
                int quantity = q.intValue();
                String swp_id = productsMap.get("swp_id").toString();

                WholesalerProductDTO wholesalerProduct = wholesalerProductService.getBySwp_id(swp_id);
                double unit_price = wholesalerProduct.getPrice();
                String pid = wholesalerProduct.getPid();
                ProductDTO product = productService.getProductById(pid);
                String productName = product.getName();
                String imageURL = product.getImage_url();

                itemsMap.put("name", productName);
                itemsMap.put("quantity", quantity);
                itemsMap.put("price", unit_price);
                itemsMap.put("image_url", imageURL);

                itemsList.add(itemsMap);
            }

            transactionMap.put("wholesaler", wholesalerName);
            transactionMap.put("location", wholesalerAddress);
            transactionMap.put("items", itemsList);

            cartTransactions.add((transactionMap));

        }


        Map<String, Object> returnCart = new HashMap<>();
        returnCart.put("cart", cartTransactions);

        return returnCart;

    }

    // returns full shopping cart entity object, including cid
    @GetMapping("/getCartNonDTO")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public ShoppingCart getCartByUID_NonDTO(@RequestParam String UID_nonDTO) throws ExecutionException, InterruptedException {
        return cartService.getCartByUID_NonDTO(UID_nonDTO);
    }

    //get cid only
    public String getCID(String UID) throws ExecutionException, InterruptedException {
        ShoppingCart cart = cartService.getCartByUID_NonDTO(UID);
        String cid = cart.getCid();
        return cid;
    }


    //add order
    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addToCart(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        //ACTION: GET ORDER DATA
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        String uen = data.get("uen").toString();

        if(quantity <= 0)
        {
            System.out.println("Error! Invalid quantity");
            return;
        }
        else if(wholesalerProductService.getBySwp_id(swp_id) == null)
        {
            System.out.println("Error! Wholesaler product does not exist");
            return;
        }

        //ACTION: ADDS TRANSACTION RECORD
        transactionController.newTransaction(data);

        // check if cart record exists for uid
        // else: create cart and add to cart
        DocumentSnapshot transactionDoc = transactionsService.findDocByUIDandStatus(uid, "IN-CART");
        if(transactionDoc == null)
        {
            System.out.println("Error adding to cart!");
            return;
        }
        String tid = transactionDoc.getId();
        double total_price = (double) transactionDoc.get("total_price");
        double price = (double) total_price;

        //ACTION: ADDS ORDER TO CART
        if(cartService.getCartByUID(uid) == null)
        {
            createCart(data, uid, tid, price);
        }
        else
        {
            String cid = getCID(uid);
            //ACTION: UPDATES CART
            cartService.updateCartByOrder(cid, quantity, tid, price);
        }

    }

    //create cart and add single order to cart
    public void createCart(@RequestBody Map<String, Object> data, String uid, String oid, double price) throws IOException, URISyntaxException
    {
        ArrayList<String> orderList = new ArrayList<String>();
        orderList.add(oid);
        //ACTION: ADDS CART RECORD
        ShoppingCartDTO cart = ShoppingCartMapper.toCartDTO(orderList, uid, price);

        //TODO: CREATE CID
        cartService.createCart(cart);
    }




    //update cart
    //new order is added to existing cart (i.e., cart already has other items)
    //or quantity is updated
    @PatchMapping("/update")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Map<String,Object> updateCart(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> returnMapFail = new HashMap<>();
        returnMapFail.put("update", null);

        ArrayList<String> transactionList = new ArrayList<>();
        float checkoutPrice = 0;
        //ACTION: GET TRANSACTION DATA
        //convert order data to array
        ArrayList<Object> cartList = (ArrayList<Object>) data.get("cart items");
        if(cartList.isEmpty())
            return returnMapFail;


        //for each transaction
        for(int i = 0; i< cartList.size(); i++)
        {
            float transactionPrice = 0;
            //from data
            Map<String, Object> transactionMap = (Map<String, Object>) cartList.get(i);
            //to update
            Map<String, Object> newTransactionMap = new HashMap<>();
            ArrayList<Object> newProductsList = new ArrayList<>();

            String wholesalerName = transactionMap.get("wholesaler").toString();
            ArrayList<Object> itemList = (ArrayList<Object>) transactionMap.get("items");

            String tid = transactionController.getTransactionFromUIDandWName(uid, wholesalerName);
            if(tid.equals("null"))
                return returnMapFail;
            transactionList.add(tid);
            TransactionsDTO transaction = transactionsService.findByTID(tid);

            String uen = transaction.getUen();

            //checkoutPrice += transactionController.updateOneTransactionAndStock(products, tid, uen);

            //ACTION: updating transactions
            for(int j = 0; j<itemList.size(); j++)
            {
                Map<String, Object> itemMap = (Map<String, Object>) itemList.get(i);
                String name = itemMap.get("name").toString();
                int quantity = Integer.parseInt(itemMap.get("quantity").toString());
                float price = (float) itemMap.get("unit_price");

                if(quantity == 0)
                    continue;

                //ACTION: get PID
                Product product = productService.findByProductName(name);
                if(product == null) return returnMapFail;
                String pid = product.getPid();

                //ACTION: get SWP_ID
                WholesalerProducts wholesalerProducts = wholesalerProductService.getWProductByPIDandUEN(pid, uen);
                if(wholesalerProducts == null) return returnMapFail;
                String swpid = wholesalerProducts.getSwpid();

                //ACTION:UPDATING DATA
                Map<String, Object> newProductMap = new HashMap<>();
                newProductMap.put("price", price);
                newProductMap.put("quantity",quantity);
                newProductMap.put("swpid", swpid);
                newProductsList.add(newProductMap);
                transactionPrice += price*quantity;
                checkoutPrice+= transactionPrice;
            }
            newTransactionMap.put("products", newProductsList);
            newTransactionMap.put("total_price", transactionPrice);

            //ACTION: UPDATES TRANSACTION DOCUMENT
            transactionsService.updateTransaction(tid, newTransactionMap);

        }

        //ACTION: UPDATES CART
        String cid = getCID(uid);
        Map<String, Object> newCart = new HashMap<>();
        newCart.put("orders", transactionList);
        newCart.put("total_price", checkoutPrice);
        cartService.updateCart(cid, newCart);

        returnMap.put("update", "success");
        return returnMap;
    }

    //delete cart item
    @PatchMapping("/delete")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCartItem(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();


        //ACTION: DELETE ITEM/ORDER FROM CART BY SWPID
        cartService.deleteItemByOID(uid, data);

        //ACTION: DELETE ITEM IN PRODUCTS LIST FROM TRANSACTIONS FIREBASE COLLECTION
        String oid_toDelete = data.get("oid").toString();

    }

    //delete whole cart from firebase (i.e., empty cart)
    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteWholeCart() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        ShoppingCart cart = cartService.getCartByUID_NonDTO(uid);
        String cid = cart.getCid();
        cartService.deleteWholeCart(cid);
    }
}
