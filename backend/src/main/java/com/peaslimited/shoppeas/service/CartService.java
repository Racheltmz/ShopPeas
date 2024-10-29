package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface CartService {

    Map<String, Object> getCartByUID(String uid) throws ExecutionException, InterruptedException;

    void addCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void updateCartQuantity(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void deleteCartProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

//    void createCart(ShoppingCartDTO orderDTO);
//
//    ShoppingCart getCartByUID_NonDTO(String UID)throws ExecutionException, InterruptedException;
//
//    void updateCart(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException;
//
//    void deleteWholeCart(String cid) throws ExecutionException, InterruptedException;
//
//    void deleteItemByOID(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
