package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of ConsumerRepository for managing consumer data in Firestore,
 * including methods to retrieve, add, and update consumer information.
 */
@Repository
public class ConsumerRepositoryImpl implements ConsumerRepository {

    private final String COLLECTION = "consumer";

    @Autowired
    private Firestore firestore;

    // Get consumer details
    /**
     * {@inheritDoc}
     *
     * Retrieves a consumer document from Firestore based on the provided UID.
     * Converts the document into a {@link ConsumerDTO} if it exists.
     *
     * @param UID the unique identifier of the consumer
     * @return a {@link ConsumerDTO} containing consumer details, or null if not found
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    @Override
    public ConsumerDTO findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Consumer object
        ConsumerDTO consumer = null;
        if (document.exists()) {
            consumer = document.toObject(ConsumerDTO.class);
        }
        return consumer;
    }

    /**
     * {@inheritDoc}
     *
     * Adds a new consumer document to Firestore with the specified UID as the document ID.
     *
     * @param UID the unique identifier of the consumer
     * @param consumer a {@link ConsumerDTO} containing details of the consumer to add
     */
    @Override
    public void addByUID(String UID, ConsumerDTO consumer) {
        firestore.collection(COLLECTION).document(UID).set(consumer);
    }

    /**
     * {@inheritDoc}
     *
     * Updates an existing consumer document in Firestore with new data based on the provided UID.
     * Each key-value pair in the {@code data} map represents a field to be updated.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link Map} containing the fields to update and their new values
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    @Override
    public void updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }
}
