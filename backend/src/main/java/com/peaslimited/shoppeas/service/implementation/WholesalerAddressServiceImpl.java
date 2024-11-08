package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.repository.WholesalerAddressRepository;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link WholesalerAddressService} interface that handles operations related to wholesaler address functionality.
 * This service provides methods to retrieve, add, and update transaction items.
 */
@Service
public class WholesalerAddressServiceImpl implements WholesalerAddressService {

    /**
     * Repository for interacting with wholesaler address data
     */
    @Autowired
    private WholesalerAddressRepository wholesalerAddressRepository;

    /**
     * Retrieves the wholesaler address based on the UEN.
     * @param UEN Wholesaler UEN.
     * @return WholesalerAddressDTO containing the necessary information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public WholesalerAddressDTO getWholesalerAddress(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerAddressRepository.findByUEN(UEN);
    }

    /**
     * Add wholesaler address information for a newly registered wholesaler.
     * @param UEN Wholesaler UEN
     * @param wholesalerAddress Wholesaler address information.
     */
    @Override
    public void addWholesalerAddress(String UEN, WholesalerAddressDTO wholesalerAddress) {
        wholesalerAddressRepository.addByUEN(UEN, wholesalerAddress);
    }

    /**
     * Update wholesaler address information based on UEN.
     * @param UEN Wholesaler UEN
     * @param data Wholesaler address information for updates.
     */
    @Override
    public void updateWholesalerAddress(String UEN, Map<String, Object> data) {
        wholesalerAddressRepository.updateByUEN(UEN, data);
    }

}
