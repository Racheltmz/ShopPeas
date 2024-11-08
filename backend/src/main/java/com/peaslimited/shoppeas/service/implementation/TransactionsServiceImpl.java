package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.model.Transactions;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.OrderHistoryService;
import com.peaslimited.shoppeas.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link TransactionsService} interface that handles operations related to transaction functionality.
 * This service provides methods to retrieve, add, and update transaction items.
 */
@Service
public class TransactionsServiceImpl implements TransactionsService {

    /**
     * Repository for interacting with transaction data
     */
    @Autowired
    private TransactionsRepository transactionsRepo;

    /**
     * Repository for interacting with cart data
     */
    @Autowired
    private CartRepository cartRepository;

    /**
     * Service for handling order history records.
     */
    @Autowired
    private OrderHistoryService orderHistoryService;

    /**
     * Retrieves the transaction by transaction ID.
     * @param tid The unique identifier of the transaction.
     * @return A TransactionDTO object.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException {
        return transactionsRepo.findByTID(tid);
    }

    /**
     * Retrieves transaction by UEN and Status
     * @param uen Wholesaler UEN.
     * @param status Transaction Status.
     * @return a list of transactions.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException {
        return transactionsRepo.getDocByUENAndStatus(uen, status);
    }

    /**
     * Get products in transaction.
     * @param document Transaction document from the database.
     * @param cart a boolean indicating if the transaction is a cart transaction
     * @return List of products.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException {
        return transactionsRepo.getProductListfromTransaction(document, cart);
    }

    /**
     * Update transaction product details.
     * @param uid Consumer UID.
     * @param data Transaction product details to update.
     * @return Transaction ID.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public String updateTransactionProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        String uen = data.get("uen").toString();
        Transactions transaction = transactionsRepo.findCartTransaction(uid, uen);

        // ACTION: transaction record exists and product is from the same wholesaler
        if (transaction != null) {
            // update existing transaction record
            transactionsRepo.updateTransactionProduct(transaction, uid, data);
            return transaction.getTid();
        } else { // ACTION: transaction record exists and product is from different wholesaler or transaction record DNE
            // input: Single "item": swp_id, quantity, uen
            String swp_id = data.get("swp_id").toString();
            double price = Double.parseDouble(data.get("price").toString());
            int quantity = Integer.parseInt(data.get("quantity").toString());
            double total_price = Double.parseDouble(data.get("total_price").toString());
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("quantity", quantity);
            productMap.put("price", price);
            productMap.put("swp_id", swp_id);

            Map<String, Object> productsMapNew = new HashMap<>();
            productsMapNew.put(String.valueOf(0), productMap);

            return transactionsRepo.addTransaction(new TransactionsDTO(
                    productsMapNew,
                    false,
                    "IN-CART",
                    total_price,
                    uen,
                    uid
            ));
        }
    }

    /**
     * Update transaction status.
     * @param data Details of transaction.
     */
    @Override
    public void updateTransactionStatus(Map<String, Object> data) {
        transactionsRepo.updateTransactionStatus(data);
    }

    /**
     * Update order history, transaction, and cart when user checks out.
     * @param uid Consumer UID.
     * @param data Transaction details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws IOException If an I/O error occurs.
     * @throws URISyntaxException If a string cannot be passed as a URI reference.
     */
    @Override
    public void updateToCheckout(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        //ACTION: GET TRANSACTION DATA
        //convert order data to array
        ArrayList<Object> cartList = (ArrayList<Object>) data.get("cart_items");

        // Check max number of orders in the cart
        if(cartList.size() > 10){
            throw new IllegalArgumentException("Too many orders in cart. Maximum allowed is 10");
        }

        // ACTION: add order history
        orderHistoryService.addOrderHistory(uid, cartList);
        System.out.println("Order history added successfully for UID: " + uid);

        // ACTION: delete cart
        String cid = cartRepository.findCIDByUID(uid);
        cartRepository.deleteCartOnCheckout(cid);
    }
}
