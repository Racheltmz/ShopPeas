package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;
import com.peaslimited.shoppeas.repository.OrderHistoryRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This interface provides methods for retrieving and managing order history for consumers such as retrieving a user's order
 * history or adding a new order to their order history.
 */
public interface OrderHistoryService {

    /**
     * Retrieves a consumer's entire order history (past orders)for a given user ID (UID) using the {@link OrderHistoryRepository}.
     *
     * @param uid The user ID for the user whose order history is to be fetched.
     * @return A list of {@link OrderHistoryDTO} objects representing the user's past orders.
     * @throws ExecutionException If an error occurs during the execution of a task that retrieves the order history.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for a result.
     */
    List<OrderHistoryDTO> getOrderHistory(String uid) throws ExecutionException, InterruptedException;

    /**
     * Adds a new order to the order history of a given consumer using the {@link OrderHistoryRepository}
     *
     * @param uid The user ID whose order history will be updated with the new order.
     * @param cartList A list of objects representing the items in the user's cart that will be added to the order history.
     * @throws ExecutionException If an error occurs during the execution of a task that adds the order history.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for a result.
     * @throws IOException If an I/O error occurs while adding the order history.
     * @throws URISyntaxException If there is a syntax error in the URI when interacting with external resources.
     */
    void addOrderHistory(String uid, ArrayList<Object> cartList) throws ExecutionException, InterruptedException, IOException, URISyntaxException;
}
