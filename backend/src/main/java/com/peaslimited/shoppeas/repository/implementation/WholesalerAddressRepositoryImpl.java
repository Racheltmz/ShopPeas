package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.repository.WholesalerAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of WholesalerAddressRepository for managing wholesaler address data in Firestore,
 * including methods to retrieve, add, and update address information for wholesalers.
 */
@Repository
public class WholesalerAddressRepositoryImpl implements WholesalerAddressRepository {

    private final String COLLECTION = "wholesaler_address";

    @Autowired
    private Firestore firestore;

     /**
     * {@inheritDoc}
     * Retrieves the address details for a wholesaler from Firestore using the specified UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link WholesalerAddressDTO} containing the address details of the wholesaler,
     * @throws ExecutionException  
     * @throws InterruptedException 
     */
    @Override
    public WholesalerAddressDTO findByUEN(String UEN) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UEN);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        WholesalerAddressDTO wholesalerAddressDTO = new WholesalerAddressDTO();
        if (document.exists()) {
            wholesalerAddressDTO = document.toObject(WholesalerAddressDTO.class);
            return wholesalerAddressDTO;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * Retrieves the addresses of all wholesalers associated with specific products.
     *
     * @param wholesalerProducts a list of {@link WholesalerProducts} related to the wholesalers
     * @return a list of {@link WholesalerAddress} objects containing the addresses of each wholesaler
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    @Override
    public List<WholesalerAddress> findAllWholesalerAddress(List<WholesalerProducts> wholesalerProducts) throws ExecutionException, InterruptedException {
        List<DocumentReference> wholesalerAddressRefs = wholesalerProducts.stream()
                .map(doc -> firestore.collection(COLLECTION).document(doc.getUen()))
                .toList();
        List<DocumentSnapshot> wholesalerAddressDocs = firestore.getAll(wholesalerAddressRefs.toArray(new DocumentReference[0])).get();

        return wholesalerAddressDocs.stream()
                .filter(DocumentSnapshot::exists)
                .map(doc -> doc.toObject(WholesalerAddress.class))
                .toList();
    }

    /**
     * {@inheritDoc}
     * Adds a new address entry for a wholesaler in Firestore using the provided UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param data a {@link WholesalerAddressDTO} containing the address details to be added
     */
    @Override
    public void addByUEN(String UEN, WholesalerAddressDTO data) {
        firestore.collection(COLLECTION).document(UEN).set(data);
    }

    /**
     * {@inheritDoc}
     * Updates an existing address entry for a wholesaler in Firestore based on the provided UEN and data map.
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
