package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.WholesalerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class WholesalerServiceImpl implements WholesalerService {
    private final WholesalerRepository wholesalerRepository;

    public WholesalerServiceImpl(WholesalerRepository wholesalerRepository) {
        this.wholesalerRepository = wholesalerRepository;
    }

    // Get wholesaler details
    @Override
    public Wholesaler getWholesaler(String UID) throws ExecutionException, InterruptedException {
        return wholesalerRepository.getWholesaler(UID);
    }

    @Override
    public void addWholesaler(String UID, Wholesaler wholesaler) {
        wholesalerRepository.addWholesaler(UID, wholesaler);
    }
}
