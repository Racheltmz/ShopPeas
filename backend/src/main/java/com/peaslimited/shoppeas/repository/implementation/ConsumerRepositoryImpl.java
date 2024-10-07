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

@Repository
public class ConsumerRepositoryImpl implements ConsumerRepository {

    private final String COLLECTION = "consumer";

    @Autowired
    private Firestore firestore;

    // Get consumer details
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

    @Override
    public void addByUID(String UID, ConsumerDTO consumer) {
        firestore.collection(COLLECTION).document(UID).set(consumer);
    }

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
