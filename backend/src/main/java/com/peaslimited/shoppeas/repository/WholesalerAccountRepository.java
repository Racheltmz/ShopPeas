package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * WholesalerAccountRepository is an interface for managing wholesaler account data,
 * including retrieving, adding, and updating account information.
 */
public interface WholesalerAccountRepository {

    /**
     * Retrieves the account information for a wholesaler by their UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link WholesalerAccountDTO} containing the account details of the wholesaler
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    WholesalerAccountDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Adds a new account entry for a wholesaler identified by their UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param wholesalerAccount a {@link WholesalerAccountDTO} containing the account details to be added
     */
    void addByUEN(String UEN, WholesalerAccountDTO wholesalerAccount);

    /**
     * Updates an existing account entry for a wholesaler identified by their UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param data a {@link Map} containing fields to update and their respective values
     */
    void updateByUEN(String UEN, Map<String, Object> data);
}
