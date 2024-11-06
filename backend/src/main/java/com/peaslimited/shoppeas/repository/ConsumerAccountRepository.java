package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ConsumerAccountRepository is an interface for managing consumer account data, 
 * including retrieving, adding, and updating consumer accounts.
 */
public interface ConsumerAccountRepository {
    
    /**
     * Retrieves the consumer account information based on the user's unique identifier (UID).
     *
     * @param UID the unique identifier of the consumer
     * @return a {@link ConsumerAccountDTO} containing the consumer's account details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    ConsumerAccountDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    /**
     * Adds a new consumer account entry for a specific user.
     *
     * @param UID the unique identifier of the consumer
     * @param consumerAccount a {@link ConsumerAccountDTO} containing the details of the consumer account to be added
     */
    void addByUID(String UID, ConsumerAccountDTO consumerAccount);

    /**
     * Updates an existing consumer account entry with new data for a specific user.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link Map} containing the fields and their new values to update in the consumer account
     * @throws ExecutionException
     * @throws InterruptedException
     */
    void updateByUID(String UID, Map<String, Object> data);
}