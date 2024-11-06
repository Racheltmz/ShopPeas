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

/**
 * Strategy implementation for registering a new consumer in the system.
 * This class handles the process of adding a new consumer and their address to the database.
 * It also sets custom claims for the newly registered user in Firebase Authentication.
 */
@Component
public class ConsumerRegistrationStrategy implements RegistrationStrategy {

    /**
     * Repository for interacting with Firebase Authentication.
     * Used for setting custom claims for the user in Firebase Authentication.
     */
    @Autowired
    private AuthRepository authRepository;

    /**
     * Service responsible for managing consumer data in the system.
     */
    @Autowired
    private ConsumerService consumerService;

    /**
     * Service responsible for managing consumer address data in the system.
     * Used to add the consumer's address to the database.
     */
    @Autowired
    private ConsumerAddressService consumerAddressService;

    /**
     * Registers a new consumer by creating their profile and setting necessary data.
     * This includes adding consumer details and adding the consumer's address to the system.
     *
     * @param uid The unique user ID of the consumer in Firebase Authentication.
     * @param userData A map containing the user data, including personal information and address.
     * @throws FirebaseAuthException If there is an issue setting user claims in Firebase Authentication.
     */
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

    /**
     * Creates a {@link ConsumerDTO} object from the provided user data map.
     * @param user A map containing the user data.
     * @return A `ConsumerDTO` object containing the consumer's profile information.
     */
    private ConsumerDTO createConsumerFromMap(Map<String, Object> user) {
        return new ConsumerDTO(
                user.get("first_name").toString(),
                user.get("last_name").toString(),
                user.get("email").toString(),
                user.get("phone_number").toString()
        );
    }

    /**
     * Creates a {@link ConsumerAddressDTO} object from the provided user data map.
     * @param user A map containing the user data.
     * @return A `ConsumerAddressDTO` object containing the consumer's address information.
     */
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
