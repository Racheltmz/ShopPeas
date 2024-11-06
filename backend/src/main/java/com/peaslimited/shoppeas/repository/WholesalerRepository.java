package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * WholesalerRepository is an interface for performing operations on wholesaler data 
 */
public interface WholesalerRepository {

    /**
     * Retrieves a wholesaler by their UID 
     * @param UID the unique identifier of the wholesaler 
     * @return a {@link WholesalerDTO} containing wholesaler details
     * @throws ExecutionException
     * @throws InterruptedException
     */
    WholesalerDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    /**
     * Finds a document snapshot by the wholesaler UEN 
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link DocumentSnapshot} of the wholesaler document
     * @throws ExecutionException
     * @throws InterruptedException
     */
    DocumentSnapshot findDocByUEN(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Retrieves a wholesaler by their UEN 
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link WholesalerDTO} containing wholesaler details
     * @throws ExecutionException
     * @throws InterruptedException
     */
    WholesalerDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Finds the name of a wholesaler based on their UEN.
     *
     * @param uen the unique entity number of the wholesaler
     * @return the name of the wholesaler as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    String findWholesalerName(String uen) throws ExecutionException, InterruptedException;

    /**
     * Retrieves a list of wholesalers based on a list of UENs.
     *
     * @param List<String> a list of UENs for the wholesalers
     * @return a list of {@link WholesalerDTO} objects containing details of each wholesaler
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    List<WholesalerDTO> findWholesalers(List<String> uen_list) throws ExecutionException, InterruptedException;

    /**
     * Adds a new wholesaler by their UID.
     * 
     * @param UID the unique identifier of the wholesaler
     * @param wholesaler a {@link WholesalerDTO} object containing the wholesaler's details
     */
    void addByUID(String UID, WholesalerDTO wholesaler);

    /**
     * Updates a wholesaler's details by their UID.
     * 
     * @param UID the unique identifier of the wholesaler
     * @param data a {@link Map} containing the fields and values to update
     * @return the updated UID as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    String updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Retrieves the rating of a wholesaler based on their UEN.
     * 
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link RatingDTO} containing the wholesaler's rating details
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    RatingDTO findRatingByUEN(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Updates the rating of a wholesaler based on their UEN.
     * 
     * @param UEN the unique entity number of the wholesaler
     * @param rating the new rating to set
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    void updateRatingByUEN(String UEN, Integer rating) throws ExecutionException, InterruptedException;

    /**
     * Finds a document snapshot by the wholesaler's name.
     * 
     * @param name the name of the wholesaler
     * @return a {@link DocumentSnapshot} of the wholesaler's document in Firestore
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    DocumentSnapshot findDocByWholesalerName(String name) throws ExecutionException, InterruptedException;
}
