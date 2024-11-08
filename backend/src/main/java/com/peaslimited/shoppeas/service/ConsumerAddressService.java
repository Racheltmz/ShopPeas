package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This interface provides methods to manage consumer addresses like the
 * retrieval, creation, and updating of consumer address details.
 */
public interface ConsumerAddressService {

    /**
     * Retrieves the consumer address associated with the given user ID (UID).
     *
     * @param UID The user ID of the consumer whose address is to be retrieved.
     * @return A {@link ConsumerAddressDTO} containing the consumer's address details.
     * @throws ExecutionException If an error occurs while retrieving the address data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    ConsumerAddressDTO getConsumerAddress(String UID) throws ExecutionException, InterruptedException;

    /**
     * Creates a new consumer address for the user with the given UID.
     * @param UID The unique identifier of the consumer to associate the address with.
     * @param consumerAddress The {@link ConsumerAddressDTO} object containing the address details to be added.
     */
    void addConsumerAddress(String UID, ConsumerAddressDTO consumerAddress);

    /**
     * Updates the address details of an existing consumer account identified by the given UID.
     *
     * @param UID The unique identifier of the consumer whose address is to be updated.
     * @param data A map containing the fields to be updated, where the key is the field name and the value is the new value.
     */
    void updateConsumerAddress(String UID, Map<String, Object> data);

}
