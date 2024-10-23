package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerRepository {

    WholesalerDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    DocumentSnapshot findDocByUEN(String UEN) throws ExecutionException, InterruptedException;

    WholesalerDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    String findWholesalerName(String uen) throws ExecutionException, InterruptedException;

    void addByUID(String UID, WholesalerDTO wholesaler);

    String updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    RatingDTO findRatingByUEN(String UEN) throws ExecutionException, InterruptedException;

    void updateRatingByUEN(String UEN, Integer rating) throws ExecutionException, InterruptedException;

    DocumentSnapshot findDocByWholesalerName(String name) throws ExecutionException, InterruptedException;
}
