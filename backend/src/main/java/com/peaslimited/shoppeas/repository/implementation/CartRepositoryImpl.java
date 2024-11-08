package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of CartRepository for managing shopping cart data in Firestore,
 * including methods to retrieve, update, and delete shopping cart information.
 */
@Repository
public class CartRepositoryImpl implements CartRepository {

    private final String COLLECTION = "shopping_cart";

    @Autowired
    private Firestore firestore;

    /**
     * {@inheritDoc}
     * 
     * Retrieves the document snapshot of a shopping cart from Firestore using the user's UID.
     *
     * @param UID the unique identifier of the user
     * @return a {@link DocumentSnapshot} representing the user's shopping cart document, or null if not found
     * @throws ExecutionException
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Retrieves a shopping cart for a given UID by converting the document snapshot to a {@link ShoppingCartDTO}.
     *
     * @param UID the unique identifier of the user
     * @return a {@link ShoppingCartDTO} containing the shopping cart details, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public ShoppingCartDTO findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = findDocByUID(UID);

        if (document != null) {
            return document.toObject(ShoppingCartDTO.class);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * Retrieves the unique cart identifier (CID) for a user's shopping cart document.
     *
     * @param UID the unique identifier of the user
     * @return the cart identifier (CID) as a {@link String}, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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
    /**
     * {@inheritDoc}
     *
     * Updates the shopping cart with an order, adjusting quantity and price as needed.
     *
     * @param cid the cart identifier
     * @param tid the transaction identifier
     * @param quantity the new quantity of the item
     * @param newPrice the updated price
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Adds a new shopping cart entry to Firestore.
     *
     * @param cart a {@link ShoppingCartDTO} containing the shopping cart details
     */
    @Override
    public void addCart(ShoppingCartDTO cart) {
       firestore.collection(COLLECTION).document().set(cart);
    }

    /**
     * {@inheritDoc}
     *
     * Updates the total price of the shopping cart in Firestore.
     *
     * @param cid the cart identifier
     * @param price the new price to add to the current total
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Deletes a specific transaction from the shopping cart.
     * If no transactions remain, the entire cart is deleted.
     *
     * @param cid the cart identifier
     * @param tid the transaction to be removed
     * @throws ExecutionException
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Deletes the entire shopping cart upon checkout.
     *
     * @param cid the cart identifier to be deleted
     */
    @Override
    public void deleteCartOnCheckout(String cid) {
        deleteCart(cid);
    }

    /**
     * Helper method to delete a shopping cart document from Firestore.
     *
     * @param cid the cart identifier to be deleted
     */
    private void deleteCart(String cid) {
        firestore.collection(COLLECTION).document(cid).delete();
    }

}
