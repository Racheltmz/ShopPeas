package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.model.WholesalerAddress;

import java.util.concurrent.ExecutionException;

public interface WholesalerAddressService {
    WholesalerAddress getWholesalerAddress(String UEN) throws ExecutionException, InterruptedException;
    void addWholesalerAddress(String UEN, WholesalerAddress wholesalerAddress);
}
