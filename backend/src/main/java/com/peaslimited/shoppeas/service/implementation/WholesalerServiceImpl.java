package com.peaslimited.shoppeas.service.implementation;

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

/**
 * Implementation of the {@link WholesalerService} interface that handles operations related to wholesaler functionality.
 * This service provides methods to retrieve, add, and update transaction items.
 */
@Service
public class WholesalerServiceImpl implements WholesalerService {

    /**
     * Repository for interacting with wholesaler data
     */
    @Autowired
    private WholesalerRepository wholesalerRepository;

    /**
     * Repository for interacting with transactions data
     */
    @Autowired
    private TransactionsRepository transactionsRepository;

    /**
     * Firestore connection.
     */
    @Autowired
    private Firestore firestore;

    /**
     * Retrieve wholesaler details by UID.
     * @param UID Wholesaler's UID.
     * @return WholesalerDTO containing wholesaler details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public WholesalerDTO getWholesaler(String UID) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUID(UID);
    }

    /**
     * Get wholesaler UID by UEN
     * @param UEN Wholesaler's UEN.
     * @return WholesalerDTO containing wholesaler details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public WholesalerDTO getWholesalerUID(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUEN(UEN);
    }

    /**
     * Add wholesaler.
     * @param UID Wholesaler's UID
     * @param wholesaler Wholesaler information.
     */
    @Override
    public void addWholesaler(String UID, WholesalerDTO wholesaler) {
        wholesalerRepository.addByUID(UID, wholesaler);
    }

    /**
     * Update wholesaler by UID.
     * @param UID Wholesaler's UID.
     * @param data Wholesaler information to update.
     * @return Wholesaler's UEN.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        return wholesalerRepository.updateByUID(UID, data);
    }

    /**
     * Get wholesaler's rating details by UEN.
     * @param UEN Wholesaler's UEN.
     * @return RatingDTO which contains of rating information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public RatingDTO getRatingByUEN(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findRatingByUEN(UEN);
    }

    /**
     * Add rating for wholesaler.
     * @param UEN Wholesaler's UEN.
     * @param tid Transaction ID to keep track of which transactions consumers have rated.
     * @param rating Rating score.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public void addRating(String UEN, String tid, Integer rating) throws ExecutionException, InterruptedException {
        wholesalerRepository.updateRatingByUEN(UEN, rating);
        transactionsRepository.updateTransactionRated(tid);
    }

    /**
     * Check if UEN exists.
     * @param uen Wholesaler's UEN.
     * @return boolean value of whether the UEN exists or not.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
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

    /**
     * Checks if status is valid.
     * @param status Status to validate.
     * @return boolean value of whether status is valid.
     */
    // helper function to ensure status validity
    public boolean isValidStatus(String status){
        List<String> validStatus = Arrays.asList("IN-CART","COMPLETED","PENDING-ACCEPTANCE","PENDING-COMPLETION");
        return validStatus.contains(status);
    }

    /**
     * Checks if wholesaler name and UEN is valid.
     * @param wholesaler Wholesaler name.
     * @param uen Wholesaler's UEN.
     * @return boolean value of whether the name and UEN are valid.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
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
