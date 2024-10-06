package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.model.Wholesaler;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerRepository {

    Wholesaler findByUID(String UID) throws ExecutionException, InterruptedException;

    Wholesaler findUIDByUEN(String UEN) throws ExecutionException, InterruptedException;

    void addByUID(String UID, Wholesaler wholesaler);

    String updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
