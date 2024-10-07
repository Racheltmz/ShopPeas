package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.repository.ConsumerRepository;
import com.peaslimited.shoppeas.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    // Get Consumer details
    @Override
    public ConsumerDTO getConsumer(String UID) throws ExecutionException, InterruptedException {
        return consumerRepository.findByUID(UID);
    }
    
    @Override
    public void addConsumer(String UID, ConsumerDTO consumer) {
        consumerRepository.addByUID(UID, consumer);
    }

    @Override
    public void updateConsumer(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        consumerRepository.updateByUID(UID, data);
    }
}
