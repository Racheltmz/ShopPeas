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

@Repository
public class ConsumerAccountRepositoryImpl implements ConsumerAccountRepository {

    private final String COLLECTION = "consumer_account";

    @Autowired
    private Firestore firestore;

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

    @Override
    public void addByUID(String UID, ConsumerAccountDTO data) {
        firestore.collection(COLLECTION).document(UID).set(data);
    }

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