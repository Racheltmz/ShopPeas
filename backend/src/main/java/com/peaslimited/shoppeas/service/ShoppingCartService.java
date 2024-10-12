package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ShoppingCartService {
    void createCart(String cid, ShoppingCartDTO orderDTO);

    ShoppingCartDTO getCartByCID(String cid) throws ExecutionException, InterruptedException;
    ShoppingCartDTO getCartByUID(String uid) throws ExecutionException, InterruptedException;

    void updateCartByOrder(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
