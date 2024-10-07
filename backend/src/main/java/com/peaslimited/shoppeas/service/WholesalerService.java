package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerService {

    WholesalerDTO getWholesaler(String UID) throws ExecutionException, InterruptedException;

    WholesalerDTO getWholesalerUID(String UEN) throws ExecutionException, InterruptedException;

    void addWholesaler(String UID, WholesalerDTO wholesaler);

    String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    RatingDTO getRatingByUEN(String UEN) throws ExecutionException, InterruptedException;

    void addRating(String UEN, Integer rating) throws ExecutionException, InterruptedException;
}
