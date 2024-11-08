package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;
import com.peaslimited.shoppeas.repository.ConsumerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of ConsumerAccountRepositry for managing consumer account data in Firestore,
 * including methods to retrieve, add, and update consumer accounts.
 */
@Repository
public class ConsumerAccountRepositoryImpl implements ConsumerAccountRepository {

    private final String COLLECTION = "consumer_account";

    @Autowired
    private Firestore firestore;

    /**
     * {@inheritDoc}
     *
     * Retrieves a consumer account document from Firestore based on the UID.
     * Converts the document into a {@link ConsumerAccountDTO} if it exists.
     *
     * @param UID the unique identifier of the consumer
     * @return a {@link ConsumerAccountDTO} containing the consumer's account details, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public ConsumerAccountDTO findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Consumer object
        ConsumerAccountDTO consumerAccountDTO = null;
        if (document.exists()) {
            consumerAccountDTO = document.toObject(ConsumerAccountDTO.class);
        }
        return consumerAccountDTO;
    }

    /**
     * {@inheritDoc}
     *
     * Adds a new consumer account document to Firestore for a specified UID.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link ConsumerAccountDTO} containing the details of the consumer account to be added
     */
    @Override
    public void addByUID(String UID, ConsumerAccountDTO data) {
        firestore.collection(COLLECTION).document(UID).set(data);
    }

    /**
     * {@inheritDoc}
     *
     * Updates an existing consumer account document in Firestore for a specified UID with new data.
     * Each key-value pair in the map represents a field to be updated.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link Map} containing the fields and their new values to update in the consumer account
     */
    @Override
    public void updateByUID(String UID, Map<String, Object> data) {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }
}
