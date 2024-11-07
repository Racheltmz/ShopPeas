package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.repository.WholesalerAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of WholesalerAccountRepository for managing wholesaler account data in Firestore,
 * including retrieving, adding, and updating account information.
 */
@Repository
public class WholesalerAccountRepositoryImpl implements WholesalerAccountRepository {

    private final String COLLECTION = "wholesaler_account";

    @Autowired
    private Firestore firestore;

    /**
     * {@inheritDoc}
     * Fetches the account information for a wholesaler from Firestore using the provided UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link WholesalerAccountDTO} containing the account details of the wholesaler
     * @throws ExecutionException
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     * Adds a new account entry for a wholesaler to Firestore based on the provided UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param data a {@link WholesalerAccountDTO} containing the account details to be added
     */
    @Override
    public void addByUEN(String UEN, WholesalerAccountDTO data) {
        firestore.collection(COLLECTION).document(UEN).set(data);
    }

    /**
     * {@inheritDoc}
     * Updates an existing account entry for a wholesaler in Firestore based on the provided UEN and data map.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param data a {@link Map} containing fields to update and their respective values
     */
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
