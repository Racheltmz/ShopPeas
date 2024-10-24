package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.model.WholesalerProducts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerAddressRepository {

    WholesalerAddressDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    List<WholesalerAddress> findAllWholesalerAddress(List<WholesalerProducts> wholesalerProducts) throws ExecutionException, InterruptedException;

    void addByUEN(String UEN, WholesalerAddressDTO wholesalerAddress);

    void updateByUEN(String UEN, Map<String, Object> data);

}
