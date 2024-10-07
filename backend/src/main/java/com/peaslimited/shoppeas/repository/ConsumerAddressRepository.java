package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ConsumerAddressRepository {

    ConsumerAddressDTO findByUID(String UID) throws ExecutionException, InterruptedException;

    void addByUID(String UID, ConsumerAddressDTO consumerAddress);

    void updateByUID(String UID, Map<String, Object> data);

}
