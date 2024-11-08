package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.repository.ConsumerAddressRepository;
import com.peaslimited.shoppeas.service.ConsumerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link ConsumerAddressService} interface, providing methods to manage consumer addresses.
 * This service allows retrieval, creation, and updating of consumer address details.
 */
@Service
public class ConsumerAddressServiceImpl implements ConsumerAddressService {

    /**
     * Repository for interacting with consumer address data.
     */
    @Autowired
    private ConsumerAddressRepository consumerAddressRepository;

    /**
     * Retrieves the consumer address associated with the given user ID (UID).
     *
     * @param UID The user ID of the consumer whose address is to be retrieved.
     * @return A {@link ConsumerAddressDTO} containing the consumer's address details.
     * @throws ExecutionException If an error occurs while retrieving the address data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public ConsumerAddressDTO getConsumerAddress(String UID) throws ExecutionException, InterruptedException {
        return consumerAddressRepository.findByUID(UID);
    }

    /**
     * Creates a new consumer address for the user with the given UID.
     * @param UID The unique identifier of the consumer to associate the address with.
     * @param consumerAddress The {@link ConsumerAddressDTO} object containing the address details to be added.
     */
    @Override
    public void addConsumerAddress(String UID, ConsumerAddressDTO consumerAddress) {
        consumerAddressRepository.addByUID(UID, consumerAddress);
    }

    /**
     * Updates the address details of an existing consumer account identified by the given UID.
     *
     * @param UID The unique identifier of the consumer whose address is to be updated.
     * @param data A map containing the fields to be updated, where the key is the field name and the value is the new value.
     */
    @Override
    public void updateConsumerAddress(String UID, Map<String, Object> data) {
        consumerAddressRepository.updateByUID(UID, data);
    }
}
