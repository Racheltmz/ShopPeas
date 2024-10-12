package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ShoppingCartRepository {
    void addByCID(String CID, ShoppingCartDTO cartDTO);

    ShoppingCartDTO findByCID(String cid) throws ExecutionException, InterruptedException;
    DocumentSnapshot findDocByUID(String UID) throws ExecutionException, InterruptedException;
    ShoppingCartDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    void updateCartWithOrder(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
