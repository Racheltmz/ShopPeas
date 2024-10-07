package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.model.Wholesaler;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerRepository {

    Wholesaler findByUID(String UID) throws ExecutionException, InterruptedException;

    DocumentSnapshot findDocByUEN(String UEN) throws ExecutionException, InterruptedException;

    Wholesaler findByUEN(String UEN) throws ExecutionException, InterruptedException;

    void addByUID(String UID, Wholesaler wholesaler);

    String updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    RatingDTO findRatingByUEN(String UEN) throws ExecutionException, InterruptedException;

    void updateRatingByUEN(String UEN, Integer rating) throws ExecutionException, InterruptedException;
}
