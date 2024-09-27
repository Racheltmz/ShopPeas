package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class WholesalerRepositoryImpl implements WholesalerRepository {
    private final Firestore firestore;

    public WholesalerRepositoryImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    // Get wholesaler details
    @Override
    public Wholesaler getWholesaler(String UID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("wholesaler").document(UID);

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
    public void addWholesaler(String UID, Wholesaler wholesaler) {
        ApiFuture<WriteResult> future = firestore.collection("wholesaler").document(UID).set(wholesaler);
    }
}
