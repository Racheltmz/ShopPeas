package com.peaslimited.shoppeas.service;

import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface CartService {

    Map<String, Object> getCartByUID(String uid) throws ExecutionException, InterruptedException;

    void addCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException, ResponseStatusException;

    void updateCartQuantity(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void deleteCartProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    boolean checkQuantity(int quantity);

    boolean checkObjectNull(Object obj);

    int testException(int i);
}
