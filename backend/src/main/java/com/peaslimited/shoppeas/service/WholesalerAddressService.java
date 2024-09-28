package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerAddressService {

    WholesalerAddressDTO getWholesalerAddress(String UEN) throws ExecutionException, InterruptedException;

    void addWholesalerAddress(String UEN, WholesalerAddressDTO wholesalerAddress);

    void updateWholesalerAddress(String UEN, Map<String, Object> data);

}
