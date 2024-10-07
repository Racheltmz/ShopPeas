package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ConsumerAccountService {

    ConsumerAccountDTO getConsumerAccount(String UID) throws ExecutionException, InterruptedException;

    void addConsumerAccount(String UID, ConsumerAccountDTO wholesalerAccount);

    void updateConsumerAccount(String UID, Map<String, Object> data);
}
