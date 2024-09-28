package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerServiceImpl implements WholesalerService {

    @Autowired
    private WholesalerRepository wholesalerRepository;

    // Get wholesaler details
    @Override
    public Wholesaler getWholesaler(String UID) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUID(UID);
    }

    @Override
    public void addWholesaler(String UID, Wholesaler wholesaler) {
        wholesalerRepository.addByUID(UID, wholesaler);
    }

    @Override
    public String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        return wholesalerRepository.updateByUID(UID, data);
    }

}
