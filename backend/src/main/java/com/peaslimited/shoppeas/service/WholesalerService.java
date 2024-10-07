package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.model.Wholesaler;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerService {

    Wholesaler getWholesaler(String UID) throws ExecutionException, InterruptedException;

    Wholesaler getWholesalerUID(String UEN) throws ExecutionException, InterruptedException;

    void addWholesaler(String UID, Wholesaler wholesaler);

    String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    RatingDTO getRatingByUEN(String UEN) throws ExecutionException, InterruptedException;

    void addRating(String UEN, Integer rating) throws ExecutionException, InterruptedException;
}
