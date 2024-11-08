package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This interface handles operations related to the wholesaler address functionality, such as
 * retrieving, adding, and updating wholesaler addresses.
 */
public interface WholesalerAddressService {

    /**
     * Retrieves the wholesaler address based on the UEN.
     * @param UEN Wholesaler UEN.
     * @return WholesalerAddressDTO containing the necessary information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    WholesalerAddressDTO getWholesalerAddress(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Add wholesaler address information for a newly registered wholesaler.
     * @param UEN Wholesaler UEN
     * @param wholesalerAddress Wholesaler address information.
     */
    void addWholesalerAddress(String UEN, WholesalerAddressDTO wholesalerAddress);

    /**
     * Update wholesaler address information based on UEN.
     * @param UEN Wholesaler UEN
     * @param data Wholesaler address information for updates.
     */
    void updateWholesalerAddress(String UEN, Map<String, Object> data);

}
