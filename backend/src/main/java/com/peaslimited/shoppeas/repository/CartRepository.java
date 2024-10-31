package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;

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
}
