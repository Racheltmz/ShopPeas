package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.controller.TransactionController;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final String COLLECTION = "shopping_cart";

    @Autowired
    private Firestore firestore;

    @Override
    public DocumentSnapshot findDocByUID(String UID) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uid", UID).get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();


        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        // Check if any documents match
        DocumentSnapshot document = null;
        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            document = documents.getFirst();
        }

        return document;
    }

    @Override
    public ShoppingCartDTO findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = findDocByUID(UID);

        if (document != null) {
            return document.toObject(ShoppingCartDTO.class);
        }
        return null;
    }

    @Override
    public String findCIDByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = findDocByUID(UID);
        if (document != null) {
            return document.getId();
        }
        return null;
    }

    //new order is added to an existing cart (i.e., cart already has other items)
    //or quantity is updated
    //cart/ order repos are then updated
    @Override
    public void updateCartWithOrder(String cid, String tid, int quantity, double newPrice) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        ShoppingCartDTO cart = null;
        if (document.exists()) {
            cart = document.toObject(ShoppingCartDTO.class);
            assert cart != null;

            // Update order list and price
            ArrayList<String> orderList = cart.getOrders();
            if (!orderList.contains(tid)) {
                orderList.add(tid);
            }

            double price = cart.getTotal_price();
            price += newPrice;

            docRef.update("orders", orderList);
            docRef.update("total_price", Double.parseDouble(String.format("%.2f", price)));
        }
    }

    @Override
    public void addCart(ShoppingCartDTO cart) {
       firestore.collection(COLLECTION).document().set(cart);
    }

    @Override
    public void updateCartPrice(String cid, double price) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        ShoppingCartDTO cart = null;
        if (document.exists()) {
            cart = document.toObject(ShoppingCartDTO.class);
            assert cart != null;
            docRef.update("total_price", Double.parseDouble(String.format("%.2f", cart.getTotal_price() + price)));
        }
    }

    @Override
    public void deleteTransaction(String cid, String tid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        ShoppingCartDTO cart = null;
        if (document.exists()) {
            cart = document.toObject(ShoppingCartDTO.class);
            assert cart != null;

            ArrayList<String> transactions = cart.getOrders();
            transactions.remove(tid);

            // If no transactions left, remove cart, else update the transactions list
            if (transactions.isEmpty()) {
                deleteCart(cid);
            } else {
                docRef.update("orders", transactions);
            }
        }
    }

    @Override
    public void deleteCartOnCheckout(String cid) {
        deleteCart(cid);
    }

    private void deleteCart(String cid) {
        firestore.collection(COLLECTION).document(cid).delete();
    }

//    @Override
//    public void addByCID(ShoppingCartDTO cart) {
//        firestore.collection(COLLECTION).document().set(cart);
//    }
//
//    @Override
//    public DocumentSnapshot findDocByUID(String UID) throws ExecutionException, InterruptedException {
//        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uid", UID).get();
//
//        // Asynchronously retrieve the document
//        QuerySnapshot querySnapshot = query.get();
//
//
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//
//        // Check if any documents match
//        DocumentSnapshot document = null;
//        if (!documents.isEmpty()) {
//            // Get the first matching document and return its ID
//            document = documents.getFirst();
//        }
//
//        return document;
//    }
//
//
//
//    //ACTION: RETURNS THE FULL ENTITY OBJECT, NOT DTO
//    @Override
//    public ShoppingCart findByUID_NonDTO(String UID) throws ExecutionException, InterruptedException {
//        DocumentSnapshot document = findDocByUID(UID);
//        String cid = document.getId();
//        if(document != null)
//        {
//            ShoppingCart cart = document.toObject(ShoppingCart.class);
//            cart.setCid(cid);
//            return cart;
//        }
//        return null;
//    }
//
//
//
//    @Override
//    public void updateCart(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException {
//        // Update an existing document
//        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
//
//        // Update fields
//        for (String key : data.keySet()) {
//            docRef.update(key, data.get(key));
//        }
//    }
//
//    public OrderDTO findByOID(String OID) throws ExecutionException, InterruptedException {
//        DocumentReference docRef = firestore.collection("orders").document(OID);
//
//        // Asynchronously retrieve the document
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//
//        // Convert document to Wholesaler object
//        OrderDTO order = null;
//        if (document.exists()) {
//            order = document.toObject(OrderDTO.class);
//        }
//        return order;
//    }
//
//    @Override
//    public void deleteWholeCart(String cid) throws ExecutionException, InterruptedException
//    {
//        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
//        docRef.delete();
//    }
//
//    @Override
//    public void deleteCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
//        String oid_toDelete = data.get("oid").toString();
//        ShoppingCart cart = findByUID_NonDTO(uid);
//        String cid = cart.getCid();
//
//        ArrayList<String> currOrderList = cart.getOrders();
//        currOrderList.remove(String.valueOf(oid_toDelete));
//
//        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
//        docRef.update("orders", currOrderList);
//    }


}