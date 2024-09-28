package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerAccountService {

    WholesalerAccountDTO getWholesalerAccount(String UEN) throws ExecutionException, InterruptedException;

    void addWholesalerAccount(String UEN, WholesalerAccountDTO wholesalerAccount);

    void updateWholesalerAccount(String UEN, Map<String, Object> data);
}
