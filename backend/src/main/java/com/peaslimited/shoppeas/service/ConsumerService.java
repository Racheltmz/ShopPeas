package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.model.Consumer;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ConsumerService {
    
    ConsumerDTO getConsumer(String UID) throws ExecutionException, InterruptedException;

    void addConsumer(String UID, ConsumerDTO consumer);

    void updateConsumer(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
