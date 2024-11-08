package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.repository.ConsumerRepository;
import com.peaslimited.shoppeas.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link ConsumerService} interface, providing methods to manage consumer details.
 * This service allows for retrieving, adding, and updating consumer data.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    /**
     * Repository for interacting with consumer data.
     */
    @Autowired
    private ConsumerRepository consumerRepository;

    /**
     * Retrieves the details of a consumer associated with the given user ID (UID).
     *
     * @param UID The user ID of the consumer whose details are to be retrieved.
     * @return A {@link ConsumerDTO} object containing the details of the consumer.
     * @throws ExecutionException If an error occurs while retrieving the consumer data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public ConsumerDTO getConsumer(String UID) throws ExecutionException, InterruptedException {
        return consumerRepository.findByUID(UID);
    }

    /**
     * Adds a new consumer and associates it with the given UID.
     *
     * @param UID The user ID of the consumer to add.
     * @param consumer The {@link ConsumerDTO} object containing the consumer's details.
     */
    @Override
    public void addConsumer(String UID, ConsumerDTO consumer) {
        consumerRepository.addByUID(UID, consumer);
    }

    /**
     * Updates the details of an existing consumer identified by the given UID.
     * The update is done by providing a map of key-value pairs representing the fields to be updated.
     *
     * @param UID The user ID of the consumer whose details are to be updated.
     * @param data A map containing the fields to be updated, where the key is the field name and the value is the new value.
     * @throws ExecutionException If an error occurs while updating the consumer data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public void updateConsumer(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        consumerRepository.updateByUID(UID, data);
    }
}
