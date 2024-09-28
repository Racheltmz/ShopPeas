package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.model.Wholesaler;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerService {

    Wholesaler getWholesaler(String UID) throws ExecutionException, InterruptedException;

    void addWholesaler(String UID, Wholesaler wholesaler);

    String updateWholesaler(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
