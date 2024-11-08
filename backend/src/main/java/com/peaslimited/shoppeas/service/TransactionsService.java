package com.peaslimited.shoppeas.service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This interface handles operations related to the transaction functionality, such as
 * retrieving, adding, and updating transaction items.
 */
public interface TransactionsService {

    /**
     * Retrieves the transaction by transaction ID.
     * @param tid The unique identifier of the transaction.
     * @return A TransactionDTO object.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException;

    /**
     * Retrieves transaction by UEN and Status
     * @param uen Wholesaler UEN.
     * @param status Transaction Status.
     * @return a list of transactions.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException;

    /**
     * Get products in transaction.
     * @param document Transaction document from the database.
     * @param cart a boolean indicating if the transaction is a cart transaction
     * @return List of products.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException;

    /**
     * Update transaction product details.
     * @param uid Consumer UID.
     * @param data Transaction product details to update.
     * @return Transaction ID.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    String updateTransactionProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Update transaction status.
     * @param data Details of transaction.
     */
    void updateTransactionStatus(Map<String, Object> data);

    /**
     * Update order history, transaction, and cart when user checks out.
     * @param uid Consumer UID.
     * @param data Transaction details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws IOException If an I/O error occurs.
     * @throws URISyntaxException If a string cannot be passed as a URI reference.
     */
    void updateToCheckout(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException;

}
