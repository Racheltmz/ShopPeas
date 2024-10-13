package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ShoppingCartService {
    void createCart(String cid, ShoppingCartDTO orderDTO);

    ShoppingCartDTO getCartByCID(String cid) throws ExecutionException, InterruptedException;
    ShoppingCartDTO getCartByUID(String uid) throws ExecutionException, InterruptedException;
    ShoppingCart getCartByUID_NonDTO(String UID)throws ExecutionException, InterruptedException;
    void updateCartByOrder(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void deleteWholeCart(String cid) throws ExecutionException, InterruptedException;
    void deleteItemByOID(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
