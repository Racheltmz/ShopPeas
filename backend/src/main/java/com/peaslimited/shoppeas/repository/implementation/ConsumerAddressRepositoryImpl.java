package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.repository.ConsumerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of ConsumerAddressRepository for managing consumer address data in Firestore,
 * including methods to retrieve, add, and update consumer addresses.
 */
@Repository
public class ConsumerAddressRepositoryImpl implements ConsumerAddressRepository {

    private final String COLLECTION = "consumer_address";

    @Autowired
    private Firestore firestore;

    /**
     * {@inheritDoc}
     *
     * Retrieves a consumer address document from Firestore based on the UID.
     * Converts the document into a {@link ConsumerAddressDTO} if it exists.
     *
     * @param UID the unique identifier of the consumer
     * @return a {@link ConsumerAddressDTO} containing the consumer's address details, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public ConsumerAddressDTO findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Consumer object
        ConsumerAddressDTO consumerAddressDTO = null;
        if (document.exists()) {
            consumerAddressDTO = document.toObject(ConsumerAddressDTO.class);
        }
        return consumerAddressDTO;
    }

    /**
     * {@inheritDoc}
     *
     * Adds a new consumer address document to Firestore for a specified UID.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link ConsumerAddressDTO} containing the details of the consumer address to be added
     */
    @Override
    public void addByUID(String UID, ConsumerAddressDTO data) {
        firestore.collection(COLLECTION).document(UID).set(data);
    }

    /**
     * {@inheritDoc}
     *
     * Updates an existing consumer address document in Firestore for a specified UID with new data.
     * Each key-value pair in the {@code data} map represents a field to be updated.
     *
     * @param UID the unique identifier of the consumer
     * @param data a {@link Map} containing the fields and their new values to update in the consumer address
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
