package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerAddressRepository {

    WholesalerAddressDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    void addByUEN(String UEN, WholesalerAddressDTO wholesalerAddress);

    void updateByUEN(String UEN, Map<String, Object> data);

}
