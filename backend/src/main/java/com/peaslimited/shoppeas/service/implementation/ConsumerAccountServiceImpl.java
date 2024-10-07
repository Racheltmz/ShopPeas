package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;
import com.peaslimited.shoppeas.repository.ConsumerAccountRepository;
import com.peaslimited.shoppeas.service.ConsumerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ConsumerAccountServiceImpl implements ConsumerAccountService {
    
    @Autowired
    private ConsumerAccountRepository consumerAccountRepository;

    @Override
    public ConsumerAccountDTO getConsumerAccount(String UID) throws ExecutionException, InterruptedException {
        return consumerAccountRepository.findByUID(UID);
    }

    @Override
    public void addConsumerAccount(String UID, ConsumerAccountDTO consumerAccount) {
        consumerAccountRepository.addByUID(UID, consumerAccount);
    }

    @Override
    public void updateConsumerAccount(String UID, Map<String, Object> data) {
        consumerAccountRepository.updateByUID(UID, data);
    }
}
