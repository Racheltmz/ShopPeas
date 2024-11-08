package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This interface handles operations related to the wholesaler account functionality, such as
 * retrieving, adding, and updating wholesaler accounts.
 */
public interface WholesalerAccountService {

    /**
     * Retrieves the wholesaler account based on the UEN.
     * @param UEN Wholesaler UEN.
     * @return WholesalerAccountDTO containing the necessary information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    WholesalerAccountDTO getWholesalerAccount(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Add wholesaler account information for a newly registered wholesaler.
     * @param UEN Wholesaler UEN
     * @param wholesalerAccount Wholesaler Account information.
     */
    void addWholesalerAccount(String UEN, WholesalerAccountDTO wholesalerAccount);

    /**
     * Update wholesaler account information based on UEN.
     * @param UEN Wholesaler UEN
     * @param data Wholesaler account information for updates.
     */
    void updateWholesalerAccount(String UEN, Map<String, Object> data);
}
