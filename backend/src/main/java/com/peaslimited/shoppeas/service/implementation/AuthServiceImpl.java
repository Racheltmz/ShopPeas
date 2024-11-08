package com.peaslimited.shoppeas.service.implementation;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.factory.RegistrationFactory;
import com.peaslimited.shoppeas.repository.AuthRepository;
import com.peaslimited.shoppeas.service.*;
import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Implementation of the {@link AuthService} interface, providing methods for registering and updating consumers and wholesalers
 * through the {@link RegistrationFactory} and {@link AuthRepository}.
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * Factory to create the appropriate registration strategy based on the user type (consumer or wholesaler).
     */
    @Autowired
    private RegistrationFactory registrationFactory;

    /**
     * Repository to interact with firebase for updating consumer and wholesaler information.
     */
    @Autowired
    private AuthRepository authRepository;

    /**
     * Registers a consumer.
     * Uses the {@link RegistrationFactory} to fetch the correct registration strategy and perform the registration.
     *
     * @param UID The unique identifier of the user to be registered.
     * @param user A map of user data that contains the user's information.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     * @throws IOException If an error occurs while processing the registration.
     */
    @Override
    public void registerConsumer(String UID, Map<String, Object> user) throws FirebaseAuthException, IOException {
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("consumer");
        strategy.register(UID, user);
    }

    /**
     * Registers a wholesaler.
     * Uses the {@link RegistrationFactory} to fetch the correct registration strategy and perform the registration.
     *
     * @param UID The unique identifier of the user to be registered.
     * @param user A map of user data that contains the user's information.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     * @throws IOException If an error occurs while processing the registration.
     */
    @Override
    public void registerWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException, IOException {
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("wholesaler");
        strategy.register(UID, user);
    }

    /**
     * Updates the information of an existing consumer through {@link AuthRepository} .
     *
     * @param UID The unique identifier of the consumer whose information is being updated.
     * @param user A map containing the updated user data.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     */
    @Override
    public void updateConsumer(String UID, Map<String, Object> user) throws FirebaseAuthException {
        // This can remain as is or can be incorporated into the strategy if you need specific update strategies.
        authRepository.updateConsumer(UID, user);
    }

    /**
     * Updates the information of an existing wholesaler through {@link AuthRepository} .
     *
     * @param UID The unique identifier of the wholesaler whose information is being updated.
     * @param user A map containing the updated user data.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     */
    @Override
    public void updateWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException {
        // This can remain as is or can be incorporated into the strategy if you need specific update strategies.
        authRepository.updateWholesaler(UID, user);
    }
}

