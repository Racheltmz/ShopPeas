package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.model.Wholesaler;

import java.util.concurrent.ExecutionException;

public interface WholesalerRepository {
    Wholesaler getWholesaler(String UID) throws ExecutionException, InterruptedException;
    void addWholesaler(String UID, Wholesaler wholesaler);
}
