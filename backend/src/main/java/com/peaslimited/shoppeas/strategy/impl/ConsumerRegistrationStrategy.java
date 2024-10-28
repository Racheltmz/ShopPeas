package com.peaslimited.shoppeas.strategy.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import com.peaslimited.shoppeas.repository.AuthRepository;
import com.peaslimited.shoppeas.service.ConsumerService;
import com.peaslimited.shoppeas.service.ConsumerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerRegistrationStrategy implements RegistrationStrategy {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @Override
    public void register(String uid, Map<String, Object> userData) throws FirebaseAuthException {
        // Create and add consumer
        ConsumerDTO consumer = createConsumerFromMap(userData);
        // Set custom claims
        authRepository.setUserClaims(uid, Map.of("consumer", true));
        consumerService.addConsumer(uid, consumer);

        // Create and add consumer address
        ConsumerAddressDTO consumerAddress = createConsumerAddressFromMap(userData);
        consumerAddressService.addConsumerAddress(uid, consumerAddress);
    }

    private ConsumerDTO createConsumerFromMap(Map<String, Object> user) {
        return new ConsumerDTO(
                user.get("first_name").toString(),
                user.get("last_name").toString(),
                user.get("email").toString(),
                user.get("phone_number").toString()
        );
    }

    private ConsumerAddressDTO createConsumerAddressFromMap(Map<String, Object> user) {
        // Similar to your existing method
        return new ConsumerAddressDTO(
                user.get("street_name").toString(),
                user.get("unit_no") != null ? user.get("unit_no").toString() : null,
                user.get("building_name") != null ? user.get("building_name").toString() : null,
                user.get("city").toString(),
                user.get("postal_code").toString()
        );
    }
}
