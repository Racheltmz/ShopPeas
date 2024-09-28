package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerAccountRepository {

    WholesalerAccountDTO findByUEN(String UEN) throws ExecutionException, InterruptedException;

    void addByUEN(String UEN, WholesalerAccountDTO wholesalerAccount);

    void updateByUEN(String UEN, Map<String, Object> data);
}
