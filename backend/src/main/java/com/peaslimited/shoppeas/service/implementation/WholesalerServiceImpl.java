package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;
import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerServiceImpl implements WholesalerService {

    @Autowired
    private WholesalerRepository wholesalerRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private Firestore firestore;

    // Get wholesaler details
    @Override
    public WholesalerDTO getWholesaler(String UID) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUID(UID);
    }

    @Override
    public WholesalerDTO getWholesalerUID(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUEN(UEN);
    }

    @Override
    public void addWholesaler(String UID, WholesalerDTO wholesaler) {
        wholesalerRepository.addByUID(UID, wholesaler);
    }

    @Override
    public String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        return wholesalerRepository.updateByUID(UID, data);
    }

    @Override
    public RatingDTO getRatingByUEN(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findRatingByUEN(UEN);
    }

    @Override
    public void addRating(String UEN, String tid, Integer rating) throws ExecutionException, InterruptedException {
        wholesalerRepository.updateRatingByUEN(UEN, rating);
        transactionsRepository.updateTransactionRated(tid);
    }

    @Override
    public DocumentSnapshot getDocByWholesalerName(String name) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findDocByWholesalerName(name);
    }

    @Override
    public boolean UENExists(String uen) throws ExecutionException, InterruptedException {
        System.out.println("Checking existence of UEN: " + uen);

        QuerySnapshot querySnapshot = firestore.collection("wholesaler")
                .whereEqualTo("uen", uen)
                .get()
                .get();

        // Print each document retrieved from the query
        System.out.println("Total documents retrieved: " + querySnapshot.size());

         return !querySnapshot.isEmpty();
    }

    // helper function to ensure status validity
    public boolean isValidStatus(String status){
        List<String> validStatus = Arrays.asList("IN-CART","COMPLETED","PENDING-ACCEPTANCE","PENDING-COMPLETION");
        return validStatus.contains(status);
    }

    @Override
    public boolean isValidWholesalerAndUEN(String wholesaler, String uen) throws ExecutionException, InterruptedException {
        // Retrieve the wholesaler information from Firebase using UEN
        QuerySnapshot snapshot = firestore.collection("wholesaler")
                .whereEqualTo("uen", uen)
                .get()
                .get();

        // Check if there are any documents in the snapshot
        if (!snapshot.isEmpty()) {
            Wholesaler wholesalerData = snapshot.getDocuments().getFirst().toObject(Wholesaler.class);

            if (wholesalerData == null) {
                System.out.println("No wholesaler data found for the given UEN.");
                return false;
            }

            // Check if wholesaler data exists and matches the provided wholesaler name
            boolean result = wholesalerData.getName().equalsIgnoreCase(wholesaler);
            System.out.println("Does the provided name match the retrieved name? " + result);

            return result;
        }

        // Return false if no document was found for the given UEN
        System.out.println("No documents found for the provided UEN.");
        return false;
    }

}
