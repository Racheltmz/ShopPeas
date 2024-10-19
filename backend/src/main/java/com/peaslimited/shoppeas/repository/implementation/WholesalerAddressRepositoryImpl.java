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
        WholesalerAddressDTO wholesalerAddressDTO = new WholesalerAddressDTO();
        if (document.exists()) {

            /*Object building_name = document.get("building_name");
            Object city = document.get("city");
            Object street_name = document.get("street_name");
            Object unit_no = document.get("unit_no");
            Object postal_code = document.get("postal_code");

            if(building_name != null)
                wholesalerAddressDTO.setBuilding_name(building_name.toString());
            else
                wholesalerAddressDTO.setBuilding_name("null");

            if(city != null)
                wholesalerAddressDTO.setCity(city.toString());
            else
                wholesalerAddressDTO.setCity("null");

            if(street_name != null)
                wholesalerAddressDTO.setStreet_name(street_name.toString());
            else
                wholesalerAddressDTO.setStreet_name("null");

            if(unit_no != null)
                wholesalerAddressDTO.setUnit_no(unit_no.toString());
            else
                wholesalerAddressDTO.setUnit_no("null");

            if(postal_code != null)
                wholesalerAddressDTO.setPostal_code(Integer.parseInt(postal_code.toString()));
            else
                wholesalerAddressDTO.setPostal_code(null);*/


            wholesalerAddressDTO = document.toObject(WholesalerAddressDTO.class);

            return wholesalerAddressDTO;
        }
        return null;
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
