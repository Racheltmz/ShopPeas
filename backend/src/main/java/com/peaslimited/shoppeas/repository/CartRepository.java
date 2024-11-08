package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ShoppingCartDTO;

import java.util.concurrent.ExecutionException;

/**
 * CartRepository is an interface for managing shopping cart data
 * including retrieving, updating, and deleting cart information.
 */
public interface CartRepository {

    /**
     * Retrieves the document snapshot of a shopping cart by the user's unique identifier (UID).
     *
     * @param uid the unique identifier of the user
     * @return a {@link DocumentSnapshot} representing the user's shopping cart document
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    DocumentSnapshot findDocByUID(String uid) throws ExecutionException, InterruptedException;

    /**
     * Retrieves a shopping cart by the user's unique identifier (UID).
     *
     * @param uid the unique identifier of the user
     * @return a {@link ShoppingCartDTO} containing the user's shopping cart details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    ShoppingCartDTO findByUID(String uid) throws ExecutionException, InterruptedException;

    /**
     * Finds the unique cart identifier (CID) based on the user's UID.
     *
     * @param uid the unique identifier of the user
     * @return the cart identifier (CID) as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    String findCIDByUID(String uid) throws ExecutionException, InterruptedException;

    /**
     * Updates the shopping cart with a specific order, modifying quantity and price for a specified transaction.
     *
     * @param cid the cart identifier
     * @param tid the transaction identifier
     * @param quantity the new quantity of the item in the cart
     * @param newPrice the updated price for the item in the cart
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    void updateCartWithOrder(String cid, String tid, int quantity, double newPrice) throws ExecutionException, InterruptedException;

    /**
     * Adds a new shopping cart entry.
     *
     * @param cart a {@link ShoppingCartDTO} containing the details of the shopping cart to add
     */
    void addCart(ShoppingCartDTO cart);

    /**
     * Updates the total price of the shopping cart.
     *
     * @param cid the cart identifier
     * @param price the new total price of the cart
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    void updateCartPrice(String cid, double price) throws ExecutionException, InterruptedException;

    /**
     * Deletes a specific transaction from the shopping cart.
     *
     * @param cid the cart identifier
     * @param tid the transaction to be removed
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    void deleteTransaction(String cid, String tid) throws ExecutionException, InterruptedException;

    /**
     * Deletes the shopping cart upon checkout.
     *
     * @param cid the cart identifier to be deleted
     */
    void deleteCartOnCheckout(String cid);
}
