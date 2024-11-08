package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This interface handles operations related to the wholesaler functionality, such as
 * retrieving, adding, and updating wholesaler information.
 */
public interface WholesalerService {

    /**
     * Retrieve wholesaler details by UID.
     * @param UID Wholesaler's UID.
     * @return WholesalerDTO containing wholesaler details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    WholesalerDTO getWholesaler(String UID) throws ExecutionException, InterruptedException;

    /**
     * Get wholesaler UID by UEN
     * @param UEN Wholesaler's UEN.
     * @return WholesalerDTO containing wholesaler details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    WholesalerDTO getWholesalerUID(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Add wholesaler.
     * @param UID Wholesaler's UID
     * @param wholesaler Wholesaler information.
     */
    void addWholesaler(String UID, WholesalerDTO wholesaler);

    /**
     * Update wholesaler by UID.
     * @param UID Wholesaler's UID.
     * @param data Wholesaler information to update.
     * @return Wholesaler's UEN.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Get wholesaler's rating details by UEN.
     * @param UEN Wholesaler's UEN.
     * @return RatingDTO which contains of rating information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    RatingDTO getRatingByUEN(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Add rating for wholesaler.
     * @param UEN Wholesaler's UEN.
     * @param tid Transaction ID to keep track of which transactions consumers have rated.
     * @param rating Rating score.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    void addRating(String UEN, String tid, Integer rating) throws ExecutionException, InterruptedException;

    /**
     * Check if UEN exists.
     * @param uen Wholesaler's UEN.
     * @return boolean value of whether the UEN exists or not.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    boolean UENExists(String uen) throws ExecutionException, InterruptedException;

    /**
     * Checks if status is valid.
     * @param status Status to validate.
     * @return boolean value of whether status is valid.
     */
    boolean isValidStatus(String status) throws ExecutionException, InterruptedException;

    /**
     * Checks if wholesaler name and UEN is valid.
     * @param wholesaler Wholesaler name.
     * @param uen Wholesaler's UEN.
     * @return boolean value of whether the name and UEN are valid.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    boolean isValidWholesalerAndUEN(String wholesaler, String uen) throws ExecutionException, InterruptedException;
}
