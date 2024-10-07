package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ConsumerAccountRepository {
    
    ConsumerAccountDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    void addByUID(String UID, ConsumerAccountDTO consumerAccount);

    void updateByUID(String UID, Map<String, Object> data);
}