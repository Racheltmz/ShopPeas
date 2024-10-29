package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private WholesalerProductService wholesalerProductService;
    @Autowired
    private ProductService productService;

    @GetMapping("/view")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getCart() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        Map<String, Object> returnMap = cartService.getCartByUID(uid);
        if (returnMap == null) {
            returnMap = new HashMap<>();
        }
        return returnMap;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addToCart(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        cartService.addCartItem(uid, data);
    }

    // update quantity
    @PatchMapping("/update")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateCart(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        cartService.updateCartQuantity(uid, data);
    }

    @PatchMapping("/deleteItem")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        cartService.deleteCartProduct(uid, data);
    }
}