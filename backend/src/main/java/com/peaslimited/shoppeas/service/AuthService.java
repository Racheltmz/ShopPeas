package com.peaslimited.shoppeas.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.factory.RegistrationFactory;
import com.peaslimited.shoppeas.repository.AuthRepository;

import java.io.IOException;
import java.util.Map;

/**
 * This interface defines the contract for services related to
 * consumer and wholesaler account management, such as registering and updating both consumer and
 * wholesaler details.
 */
public interface AuthService {
    /**
     * Registers a consumer.
     *
     * @param UID The unique identifier of the user to be registered.
     * @param consumer A map of user data that contains the user's information.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     * @throws IOException If an error occurs while processing the registration.
     */
    void registerConsumer(String UID, Map<String, Object> consumer) throws FirebaseAuthException, IOException;

    /**
     * Updates the information of an existing consumer.
     *
     * @param UID The unique identifier of the consumer whose information is being updated.
     * @param consumer A map containing the updated user data.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     */
    void updateConsumer(String UID, Map<String, Object> consumer) throws FirebaseAuthException;

    /**
     * Registers a wholesaler.
     *
     * @param UID The unique identifier of the user to be registered.
     * @param wholesaler A map of user data that contains the user's information.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     * @throws IOException If an error occurs while processing the registration.
     */
    void registerWholesaler(String UID, Map<String, Object> wholesaler) throws FirebaseAuthException, IOException;

    /**
     * Updates the information of an existing wholesaler through {@link AuthRepository} .
     *
     * @param UID The unique identifier of the wholesaler whose information is being updated.
     * @param wholesaler A map containing the updated user data.
     * @throws FirebaseAuthException If there is an error during Firebase authentication.
     */
    void updateWholesaler(String UID, Map<String, Object> wholesaler) throws FirebaseAuthException;
}
