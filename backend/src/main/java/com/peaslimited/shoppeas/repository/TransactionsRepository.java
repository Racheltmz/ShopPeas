package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.TransactionsOrderedDTO;
import com.peaslimited.shoppeas.model.Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * TransactionReository is an interface for performing operations related to transactions
 * in the database, including finding, adding, updating, and managing transactions.
 */
public interface TransactionsRepository {

    /**
     * Finds a transaction by its unique transaction ID (tid).
     *
     * @param tid the unique transaction ID
     * @return a {@link TransactionsDTO} containing transaction details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException;

    /**
     * Finds a cart transaction based on the user's UID and the wholesaler's UEN.
     *
     * @param uid the user's unique identifier
     * @param uen the wholesaler's unique entity number
     * @return a {@link Transactions} object representing the cart transaction
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    Transactions findCartTransaction(String uid, String uen) throws ExecutionException, InterruptedException;

    /**
     * Retrieves a list of document snapshots filtered by the wholesaler's UEN and the transaction status.
     *
     * @param uen the wholesaler's unique entity number
     * @param status the status of the transaction (e.g. "COMPLETED")
     * @return a list of {@link QueryDocumentSnapshot} objects
     * @throws ExecutionException   
     * @throws InterruptedException 
     */
    List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException;

    /**
     * Extracts the product list from a transaction document.
     *
     * @param document the Firestore document snapshot of the transaction
     * @param cart a boolean indicating whether the transaction is a cart transaction
     * @return an {@link ArrayList} containing the product list from the transaction
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException;

    /**
     * Adds a new transaction to the database.
     *
     * @param transactionsDTO a {@link TransactionsDTO} containing transaction details
     * @return the ID of the added transaction as a {@link String}
     */
    String addTransaction(TransactionsDTO transactionsDTO);

    /**
     * Updates a specific product within a transaction based on user ID and provided data.
     *
     * @param transaction the {@link Transactions} object representing the transaction to update
     * @param uid the user's unique identifier
     * @param data a {@link Map} containing the update data for the transaction's product
     */
    void updateTransactionProduct(Transactions transaction, String uid, Map<String, Object> data);

    /**
     * Updates the quantity and price of a specific product in a transaction.
     *
     * @param uid the user's unique identifier
     * @param uen the wholesaler's unique entity number
     * @param swp_id the wholesaler product's unique identifier within the transaction
     * @param quantity the new quantity for the product
     * @param price the updated price for the product
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    void updateProductQuantity(String uid, String uen, String swp_id, int quantity, double price) throws ExecutionException, InterruptedException;

    /**
     * Updates the product list in a transaction.
     *
     * @param uid the user's unique identifier
     * @param uen the wholesaler's unique entity number
     * @param swp_id the wholesaler product's unique identifier within the transaction
     * @return a {@link Map} representing the updated product list data
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    Map<String, Object> updateProductList(String uid, String uen, String swp_id) throws ExecutionException, InterruptedException;

    /**
     * Retrieves historical transaction details based on the order ID.
     *
     * @param orderId the unique identifier of the order
     * @return a {@link TransactionsOrderedDTO} containing details of the ordered transaction
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    TransactionsOrderedDTO getHistoryDetails(String orderId) throws ExecutionException, InterruptedException;

    /**
     * Marks a transaction as rated based on its transaction ID (TID).
     *
     * @param tid the unique transaction ID
     */
    void updateTransactionRated(String tid);

    /**
     * Updates the status of a transaction based on provided data.
     *
     * @param data a {@link Map} containing the transaction's new status information
     */
    void updateTransactionStatus(Map<String, Object> data);

    /**
     * Updates the price of a transaction based on its transaction ID (TID).
     *
     * @param tid the unique transaction ID
     * @param updatedPrice the new price for the transaction
     */
    void updateTransactionPrice(String tid, double updatedPrice);

//
//    DocumentSnapshot getDocByUENAndWName(String uen, String uid) throws ExecutionException, InterruptedException;
//
//    void createTransaction(TransactionsDTO transactionsDTO);
//
//    void updateTransaction(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException;
  
}
