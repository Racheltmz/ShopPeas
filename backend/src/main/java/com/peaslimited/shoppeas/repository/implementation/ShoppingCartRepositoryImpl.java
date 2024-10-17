package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.OrderDTO;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private final String COLLECTION = "shopping_cart";

    @Autowired
    private Firestore firestore;

    @Override
    public void addByCID(ShoppingCartDTO cart)
    {
        firestore.collection(COLLECTION).document().set(cart);
    }

    @Override
    public ShoppingCartDTO findByCID(String CID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(CID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        ShoppingCartDTO cart = null;
        if (document.exists()) {
            cart = document.toObject(ShoppingCartDTO.class);
        }
        return cart;
    }

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

        if(document != null)
        {
            return document.toObject(ShoppingCartDTO.class);
        }
        return null;
    }

    //ACTION: RETURNS THE FULL ENTITY OBJECT, NOT DTO
    @Override
    public ShoppingCart findByUID_NonDTO(String UID) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = findDocByUID(UID);
        String cid = document.getId();
        if(document != null)
        {
            ShoppingCart cart = document.toObject(ShoppingCart.class);
            cart.setCid(cid);
            return cart;
        }
        return null;
    }

    //new order is added to existing cart (i.e., cart already has other items)
    //or quantity is updated
    //cart/ order repos are then updated
    @Override
    public void updateCartWithOrder(String cid, int quantity, String tid, double newPrice) throws ExecutionException, InterruptedException
    {
        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        ShoppingCartDTO cart = null;
        if (document.exists()) {
            cart = document.toObject(ShoppingCartDTO.class);
            ArrayList<String> orderList = cart.getOrders();
            Double price = cart.getTotal_price();


            // ACTION: UPDATE QUANTITY
            boolean updateQuantity = false;
            for(int i = 0; i < orderList.size(); i++)
            {
                if(orderList.get(i).equals(tid))
                {
                    updateQuantity = true;
                    break;
                }
            }
            if(updateQuantity)
            {
                //update in order
                DocumentReference docRefOrder = firestore.collection("transactions").document(tid);
                ApiFuture<DocumentSnapshot> futureDoc = docRef.get();
                DocumentSnapshot doc = futureDoc.get();

                int currQuantity = Integer.parseInt(doc.get("quantity").toString());
                docRefOrder.update("quantity",currQuantity + quantity);
                //update in transaction
                price += newPrice;
                docRef.update("total_price", price);
            }
            else // ACTION: ADD NEW ORDER TO EXISTING CART
            {
                orderList.add(tid);
                price += newPrice;

                docRef.update("orders", orderList);
                docRef.update("total_price", price);
            }

        }
    }

    public OrderDTO findByOID(String OID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("orders").document(OID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        OrderDTO order = null;
        if (document.exists()) {
            order = document.toObject(OrderDTO.class);
        }
        return order;
    }

    @Override
    public void deleteWholeCart(String cid) throws ExecutionException, InterruptedException
    {
        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
        docRef.delete();
    }

    @Override
    public void deleteCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        String oid_toDelete = data.get("oid").toString();
        ShoppingCart cart = findByUID_NonDTO(uid);
        String cid = cart.getCid();

        ArrayList<String> currOrderList = cart.getOrders();
        currOrderList.remove(String.valueOf(oid_toDelete));

        DocumentReference docRef = firestore.collection(COLLECTION).document(cid);
        docRef.update("orders", currOrderList);
    }


}
