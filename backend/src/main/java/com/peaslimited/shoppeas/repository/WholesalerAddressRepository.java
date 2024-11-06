package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.model.WholesalerProducts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * WholesalerAddressRepository is an interface for managing wholesaler address data,
 * including retrieving, adding, and updating address information.
 */
public interface WholesalerAddressRepository {

    /**
     * Retrieves the address information for a specific wholesaler by their UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link WholesalerAddressDTO} containing the address details of the wholesaler
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    WholesalerAddressDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    /**
     * Retrieves the addresses for a list of wholesalers associated with specific products.
     *
     * @param wholesalerProducts a list of {@link WholesalerProducts} related to the wholesalers
     * @return a list of {@link WholesalerAddress} containing the addresses of each wholesaler
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    List<WholesalerAddress> findAllWholesalerAddress(List<WholesalerProducts> wholesalerProducts) throws ExecutionException, InterruptedException;

    /**
     * Adds a new address entry for a wholesaler identified by their UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param wholesalerAddress a {@link WholesalerAddressDTO} containing the address details to be added
     */
    void addByUEN(String UEN, WholesalerAddressDTO wholesalerAddress);

    /**
     * Updates an existing address entry for a wholesaler identified by their UEN.
     *
     * @param UEN the unique entity number of the wholesaler
     * @param data a {@link Map} containing fields to update and their respective values
     */
    void updateByUEN(String UEN, Map<String, Object> data);

}
