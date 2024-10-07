package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ConsumerAddressService {

    ConsumerAddressDTO getConsumerAddress(String UID) throws ExecutionException, InterruptedException;

    void addConsumerAddress(String UID, ConsumerAddressDTO wholesalerAddress);

    void updateConsumerAddress(String UID, Map<String, Object> data);

}
