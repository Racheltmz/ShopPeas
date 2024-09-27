package com.peaslimited.shoppeas.service.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerAddressServiceImpl implements WholesalerAddressService {

    private final Firestore firestore;

    public WholesalerAddressServiceImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public WholesalerAddress getWholesalerAddress(String UEN) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("wholesaler_address").document(UEN);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        WholesalerAddress wholesalerAddress = null;
        if (document.exists()) {
            wholesalerAddress = document.toObject(WholesalerAddress.class);
        }
        return wholesalerAddress;
    }

    @Override
    public void addWholesalerAddress(String UEN, WholesalerAddress wholesalerAddress) {
        // Create a Map to represent the data
        Map<String, Object> data = new HashMap<>();
        data.put("street_name", wholesalerAddress.getStreet_name());
        data.put("unit_no", wholesalerAddress.getUnit_no());
        data.put("building_name", wholesalerAddress.getBuilding_name());
        data.put("city", wholesalerAddress.getCity());
        data.put("postal_code", wholesalerAddress.getPostal_code());
        ApiFuture<WriteResult> future = firestore.collection("wholesaler_address").document(UEN).set(data);
    }
}
