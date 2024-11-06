package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * OrderHistoryRepository is an interface for managing order history data,
 * including retrieving and adding order history records.
 */
public interface OrderHistoryRepository {

    /**
     * Retrieves the order history for a specific user based on their UID.
     *
     * @param uid the unique identifier of the user
     * @return a {@link List} of {@link OrderHistoryDTO} objects containing the user's order history
     * @throws ExecutionException
     * @throws InterruptedException
     */
    List<OrderHistoryDTO> getOrderHistoryByUID(String uid) throws ExecutionException, InterruptedException;

    /**
     * Adds a new order history entry for a user based on their UID.
     *
     * @param uid the unique identifier of the user
     * @param cartList an {@link ArrayList} containing the items from the user's cart to be added to order history
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @throws IOException 
     * @throws URISyntaxException 
     */
    void addOrderHistory(String uid, ArrayList<Object> cartList) throws ExecutionException, InterruptedException, IOException, URISyntaxException;
}
