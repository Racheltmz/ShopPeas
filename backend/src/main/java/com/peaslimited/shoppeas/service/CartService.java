package com.peaslimited.shoppeas.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface CartService {

    Map<String, Object> getCartByUID(String uid) throws ExecutionException, InterruptedException;

    void addCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void updateCartQuantity(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void deleteCartProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
