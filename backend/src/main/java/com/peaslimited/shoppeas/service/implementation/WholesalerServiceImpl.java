package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;
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
    public WholesalerDTO getWholesaler(String UID) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUID(UID);
    }

    @Override
    public WholesalerDTO getWholesalerUID(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findByUEN(UEN);
    }

    @Override
    public void addWholesaler(String UID, WholesalerDTO wholesaler) {
        wholesalerRepository.addByUID(UID, wholesaler);
    }

    @Override
    public String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        return wholesalerRepository.updateByUID(UID, data);
    }

    @Override
    public RatingDTO getRatingByUEN(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerRepository.findRatingByUEN(UEN);
    }

    @Override
    public void addRating(String UEN, Integer rating) throws ExecutionException, InterruptedException {
        wholesalerRepository.updateRatingByUEN(UEN, rating);
    }

    @Override
    public DocumentSnapshot getDocByWholesalerName(String name) throws ExecutionException, InterruptedException
    {
        return wholesalerRepository.findDocByWholesalerName(name);
    }

}
