package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ConsumerAddressRepository is an interface for managing consumer address data, 
 * including retrieval, addition, and updates of consumer addresses.
 */
public interface ConsumerAddressRepository {

    /**
     * Retrieves the consumer's address information based on the user's unique identifier (UID).
     *
     * @param UID the unique identifier of the consumer
     * @return a {@link ConsumerAddressDTO} containing the consumer's address details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    ConsumerAddressDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    /**
     * Adds a new consumer address entry for a specific user.
     *
     * @param UID the unique identifier of the consumer
     * @param consumerAddress a {@link ConsumerAddressDTO} containing the details of the consumer address to be added
     */
    void addByUID(String UID, ConsumerAddressDTO consumerAddress);

    /**
     * Updates an existing consumer address entry with new data for a specific user.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link Map} containing the fields and their new values to update in the consumer address
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    void updateByUID(String UID, Map<String, Object> data);

}
