package com.peaslimited.shoppeas.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.OrderDTO;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.dto.mapper.OrderMapper;
import com.peaslimited.shoppeas.dto.mapper.ShoppingCartMapper;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.service.OrderService;
import com.peaslimited.shoppeas.service.ShoppingCartService;
import com.peaslimited.shoppeas.service.TransactionsService;
import com.peaslimited.shoppeas.service.WholesalerTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/shoppingCart")
public class CartController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TransactionsService transactionsService;

    // TODO: @saffron delete cart

    //get cart (DTO object)
    @GetMapping("/getCart")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public ShoppingCartDTO getCartByUID(@RequestParam String UID) throws ExecutionException, InterruptedException {
        return cartService.getCartByUID(UID);
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
    @PostMapping("/addtocart")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addToCart(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        //ACTION: GET ORDER DATA
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        String uen = data.get("uen").toString();

        //ACTION: ADDS TRANSACTION RECORD
        transactionController.newTransaction(data);

        // check if cart record exists for uid
        // else: create cart and add to cart
        DocumentSnapshot transactionDoc = transactionsService.findDocByUIDandStatus(uid, "IN-CART");
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
    @PatchMapping("/addToCart/update")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateCart(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        //ACTION: GET ORDER DATA
        String oid = data.get("oid").toString();
        double price = Double.parseDouble(data.get("price").toString());
        int quantity = Integer.parseInt(data.get("quantity").toString());
        String swp_id = data.get("swp_id").toString();
        String type = "CART";

        //ACTION: ADDS ORDER RECORD
        OrderDTO order = OrderMapper.toOrderDTO(price, quantity, swp_id, type);
        orderService.addOrder(oid, order);

        // cart record exists for uid
        //String cid = data.get("cid").toString();
        String cid = getCID(uid);
        //ACTION: UPDATES CART
        //cartService.updateCartByOrder(cid, data);

    }


    //delete cart item
    @PatchMapping("/deleteSingleItem")
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
    @DeleteMapping("/delete")
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
