package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ConsumerDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ConsumerRepository is an interface for performing operations related to consumers, 
 * including methods for finding, adding, and updating consumer information. 
 */
public interface ConsumerRepository {

    /**
     * Retrieves a consumer by their unique user ID (UID).
     * @param UID the unique identifier of the consumer
     * @return a {@link ConsumerDTO} containing consumer details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    ConsumerDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    /**
     * Adds a new consumer to the database using the specified UID.
     * @param UID the unique identifier of the consumer
     * @param consumer a {@link ConsumerDTO} containing details of the consumer to add
     */
    void addByUID(String UID, ConsumerDTO consumer);

    /**
     * Updates an existing consumer's information based on their UID with the provided data.
     * @param UID the unique identifier of the consumer
     * @param data a {@link Map} containing the fields to update and their new values
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    void updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
