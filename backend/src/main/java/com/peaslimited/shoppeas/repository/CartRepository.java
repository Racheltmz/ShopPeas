package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface CartRepository {

    DocumentSnapshot findDocByUID(String uid) throws ExecutionException, InterruptedException;

    ShoppingCartDTO findByUID(String uid) throws ExecutionException, InterruptedException;

    String findCIDByUID(String uid) throws ExecutionException, InterruptedException;

    void updateCartWithOrder(String cid, String tid, int quantity, double newPrice) throws ExecutionException, InterruptedException;

    void addCart(ShoppingCartDTO cart);

    void updateCartPrice(String cid, double price) throws ExecutionException, InterruptedException;

    void deleteTransaction(String cid, String tid) throws ExecutionException, InterruptedException;

    void deleteCartOnCheckout(String cid);

//    DocumentSnapshot findDocByUID(String UID) throws ExecutionException, InterruptedException;
//    ShoppingCart findByUID_NonDTO(String UID) throws ExecutionException, InterruptedException;
//
//    void updateCart(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException;
//    void deleteWholeCart(String cid) throws ExecutionException, InterruptedException;
//    void deleteCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;
}