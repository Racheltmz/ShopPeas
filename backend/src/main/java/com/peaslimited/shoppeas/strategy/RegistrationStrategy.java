package com.peaslimited.shoppeas.strategy;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.Map;

/**
 * Interface that defines the registration strategy for different user types (e.g., consumer, wholesaler).
 */
public interface RegistrationStrategy {
    /**
     * Registers a new user by processing the provided data and saving it to the system.
     * @param uid The unique identifier for the user in Firebase Authentication.
     * @param userData A map containing the user data.
     * @throws IOException If an I/O error occurs while processing user data.
     * @throws FirebaseAuthException If there is an error when interacting with Firebase Authentication.
     */
    void register(String uid, Map<String, Object> userData) throws IOException, FirebaseAuthException;
}