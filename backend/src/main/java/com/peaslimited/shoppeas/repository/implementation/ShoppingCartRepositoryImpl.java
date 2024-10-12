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
    public void addByCID(String CID, ShoppingCartDTO cart)
    {
        firestore.collection(COLLECTION).document(CID).set(cart);
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

        // Convert document to Wholesaler object
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

    @Override
    public void updateCartWithOrder(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException
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

            String newOrderOID = data.get("oid").toString();
            Double newPrice = Double.parseDouble(data.get("price").toString());
            // ACTION: UPDATE QUANTITY
            boolean updateQuantity = false;
            for(int i = 0; i < orderList.size(); i++)
            {
                if(orderList.get(i).equals(newOrderOID))
                {
                    updateQuantity = true;
                    break;
                }
            }
            if(updateQuantity)
            {
                //update in order
                DocumentReference docRefOrder = firestore.collection("orders").document(newOrderOID);
                docRefOrder.update("price", newPrice);
                docRefOrder.update("quantity", Integer.parseInt(data.get("quantity").toString()));
                //update in transaction
                price += newPrice;
                docRef.update("total_price", price);
            }
            else // ACTION: ADD NEW ORDER TO EXISTING CART
            {
                orderList.add(newOrderOID);
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


}
