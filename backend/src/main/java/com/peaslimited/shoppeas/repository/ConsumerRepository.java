package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ConsumerDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ConsumerRepository {

    ConsumerDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    void addByUID(String UID, ConsumerDTO consumer);

    void updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
