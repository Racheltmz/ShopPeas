package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.repository.WholesalerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class WholesalerAddressRepositoryImpl implements WholesalerAddressRepository {

    private final String COLLECTION = "wholesaler_address";

    @Autowired
    private Firestore firestore;

    @Override
    public WholesalerAddressDTO findByUEN(String UEN) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UEN);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        WholesalerAddressDTO wholesalerAddressDTO = null;
        if (document.exists()) {
            wholesalerAddressDTO = document.toObject(WholesalerAddressDTO.class);
        }
        return wholesalerAddressDTO;
    }

    @Override
    public void addByUEN(String UEN, WholesalerAddressDTO data) {
        firestore.collection(COLLECTION).document(UEN).set(data);
    }

    @Override
    public void updateByUEN(String UEN, Map<String, Object> data) {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(UEN);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }
}
