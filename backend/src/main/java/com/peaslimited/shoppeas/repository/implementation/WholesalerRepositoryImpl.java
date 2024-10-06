package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class WholesalerRepositoryImpl implements WholesalerRepository {

    private final String COLLECTION = "wholesaler";

    @Autowired
    private Firestore firestore;

    // Get wholesaler details
    @Override
    public Wholesaler findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        Wholesaler wholesaler = null;
        if (document.exists()) {
            wholesaler = document.toObject(Wholesaler.class);
        }
        return wholesaler;
    }

    @Override
    public Wholesaler findUIDByUEN(String UEN) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uen", UEN).get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();

        // Convert document to Wholesaler object
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        // Check if any documents match
        Wholesaler wholesaler = null;

        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            DocumentSnapshot document = documents.getFirst();
            wholesaler = document.toObject(Wholesaler.class);
        }
        return wholesaler;
    }

    @Override
    public void addByUID(String UID, Wholesaler wholesaler) {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION).document(UID).set(wholesaler);
    }

    @Override
    public String updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Update fields
        for (String key : data.keySet()) {
            ApiFuture<WriteResult> future = docRef.update(key, data.get(key));
        }
        return findByUID(UID).getUEN();
    }
}
