package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.repository.ConsumerAddressRepository;
import com.peaslimited.shoppeas.service.ConsumerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ConsumerAddressServiceImpl implements ConsumerAddressService {

    @Autowired
    private ConsumerAddressRepository consumerAddressRepository;

    @Override
    public ConsumerAddressDTO getConsumerAddress(String UID) throws ExecutionException, InterruptedException {
        return consumerAddressRepository.findByUID(UID);
    }

    @Override
    public void addConsumerAddress(String UID, ConsumerAddressDTO consumerAddress) {
        consumerAddressRepository.addByUID(UID, consumerAddress);
    }

    @Override
    public void updateConsumerAddress(String UID, Map<String, Object> data) {
        consumerAddressRepository.updateByUID(UID, data);
    }
}
