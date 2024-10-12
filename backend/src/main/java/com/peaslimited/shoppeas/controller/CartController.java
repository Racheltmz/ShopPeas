package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.OrderDTO;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.dto.mapper.OrderMapper;
import com.peaslimited.shoppeas.dto.mapper.ShoppingCartMapper;
import com.peaslimited.shoppeas.service.OrderService;
import com.peaslimited.shoppeas.service.ShoppingCartService;
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

    // TODO: @saffron get cart, add to cart, update cart item, delete cart

    //get cart
    @GetMapping("/getCart")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public ShoppingCartDTO getCartByUID(@RequestParam String UID) throws ExecutionException, InterruptedException {
        return cartService.getCartByUID(UID);
    }

    //add order
    @PostMapping("/addToCart")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createOrder(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
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

        // check if cart record exists for uid
        // else: create cart and add to cart
        //ACTION: ADDS ORDER TO CART
        if(cartService.getCartByUID(uid) == null)
        {
            createCart(data, uid, oid, price);
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
        cartService.createCart(data.get("cid").toString(), cart);
    }


    //update cart
    @PatchMapping("/addToCart/update")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void addToCart(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
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

        // check if cart record exists for uid
        // else: create cart and add to cart
        String cid = data.get("cid").toString();
        //ACTION: UPDATES CART
        cartService.updateCartByOrder(cid, data);

    }

    //update cart order

    //delete cart item
}
