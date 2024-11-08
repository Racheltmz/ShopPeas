package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.repository.WholesalerAccountRepository;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link WholesalerAccountService} interface that handles operations related to wholesaler address functionality.
 * This service provides methods to retrieve, add, and update transaction items.
 */
@Service
public class WholesalerAccountServiceImpl implements WholesalerAccountService {

    /**
     * Repository for interacting with wholesaler account data
     */
    @Autowired
    private WholesalerAccountRepository wholesalerAccountRepository;

    /**
     * Retrieves the wholesaler account based on the UEN.
     * @param UEN Wholesaler UEN.
     * @return WholesalerAccountDTO containing the necessary information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public WholesalerAccountDTO getWholesalerAccount(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerAccountRepository.findByUEN(UEN);
    }

    /**
     * Add wholesaler account information for a newly registered wholesaler.
     * @param UEN Wholesaler UEN
     * @param wholesalerAccount Wholesaler Account information.
     */
    @Override
    public void addWholesalerAccount(String UEN, WholesalerAccountDTO wholesalerAccount) {
        wholesalerAccountRepository.addByUEN(UEN, wholesalerAccount);
    }

    /**
     * Update wholesaler account information based on UEN.
     * @param UEN Wholesaler UEN
     * @param data Wholesaler account information for updates.
     */
    @Override
    public void updateWholesalerAccount(String UEN, Map<String, Object> data) {
        wholesalerAccountRepository.updateByUEN(UEN, data);
    }
}