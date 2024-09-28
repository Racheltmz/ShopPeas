package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.repository.WholesalerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class WholesalerAccountRepositoryImpl implements WholesalerAccountRepository {

    private final String COLLECTION = "wholesaler_account";

    @Autowired
    private Firestore firestore;

    @Override
    public WholesalerAccountDTO findByUEN(String UEN) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UEN);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        WholesalerAccountDTO wholesalerAccountDTO = null;
        if (document.exists()) {
            wholesalerAccountDTO = document.toObject(WholesalerAccountDTO.class);
        }
        return wholesalerAccountDTO;
    }

    @Override
    public void addByUEN(String UEN, WholesalerAccountDTO data) {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION).document(UEN).set(data);
    }

    @Override
    public void updateByUEN(String UEN, Map<String, Object> data) {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(UEN);

        // Update fields
        for (String key : data.keySet()) {
            ApiFuture<WriteResult> future = docRef.update(key, data.get(key));
        }
    }
}
