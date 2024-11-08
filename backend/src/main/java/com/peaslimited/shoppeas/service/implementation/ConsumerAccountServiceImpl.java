package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;
import com.peaslimited.shoppeas.repository.ConsumerAccountRepository;
import com.peaslimited.shoppeas.service.ConsumerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link ConsumerAccountService} interface, providing methods to manage consumer accounts
 * such as the retrieval, creation, and updating of consumer account (payment) details.
 */
@Service
public class ConsumerAccountServiceImpl implements ConsumerAccountService {

    /**
     * Repository for interacting with consumer account data.
     */
    @Autowired
    private ConsumerAccountRepository consumerAccountRepository;

    /**
     * Retrieves the consumer account for a consumer associated with the given user ID (UID).
     *
     * @param UID The user ID of the consumer account (payment methods) to be retrieved.
     * @return A {@link ConsumerAccountDTO} object containing the consumer account details.
     * @throws ExecutionException If an error occurs while retrieving the account data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public ConsumerAccountDTO getConsumerAccount(String UID) throws ExecutionException, InterruptedException {
        return consumerAccountRepository.findByUID(UID);
    }

    /**
     * Creates a new consumer account record for a specific consumer.
     *
     * @param UID The user ID of the consumer account to create or update.
     * @param consumerAccount The {@link ConsumerAccountDTO} object containing the details of the consumer account to be added.
     */
    @Override
    public void addConsumerAccount(String UID, ConsumerAccountDTO consumerAccount) {
        consumerAccountRepository.addByUID(UID, consumerAccount);
    }

    /**
     * Updates the details of an existing consumer account for a specific consumer by providing a map of key-value pairs
     * representing the fields to be updated.
     *
     * @param UID The user ID of the consumer account to update.
     * @param data A map containing the fields to be updated, where the key is the field name and the value is the new value.
     */
    @Override
    public void updateConsumerAccount(String UID, Map<String, Object> data) {
        consumerAccountRepository.updateByUID(UID, data);
    }
}
