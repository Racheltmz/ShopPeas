package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.model.WholesalerAccount;

import java.util.concurrent.ExecutionException;

public interface WholesalerAccountService {
    WholesalerAccount getWholesalerAccount(String UEN) throws ExecutionException, InterruptedException;
    void addWholesalerAccount(String UEN, WholesalerAccount wholesalerAccount);
}
