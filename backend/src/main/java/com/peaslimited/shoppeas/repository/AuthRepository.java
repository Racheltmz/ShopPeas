package com.peaslimited.shoppeas.repository;

import com.google.firebase.auth.FirebaseAuthException;

import java.util.Map;

/**
 * AuthRepository is an interface for managing authentication and authorization operations, 
 * including setting user claims and updating user information in firebase
 */
public interface AuthRepository {

    /**
     * Sets custom claims for a specified user to manage access control.
     *
     * @param uid the unique identifier of the user
     * @param claims a {@link Map} of claim names to values, representing the custom claims to set for the user
     * @throws FirebaseAuthException
     */
    void setUserClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException;

    /**
     * Updates information for a consumer user in Firebase Authentication.
     *
     * @param uid the unique identifier of the consumer
     * @param user a {@link Map} containing fields to update and their respective values
     * @throws FirebaseAuthException 
     */
    void updateConsumer(String uid, Map<String, Object> user) throws FirebaseAuthException;

    /**
     * Updates information for a wholesaler user in Firebase Authentication.
     *
     * @param uid the unique identifier of the wholesaler
     * @param user a {@link Map} containing fields to update and their respective values
     * @throws FirebaseAuthException 
     */
    void updateWholesaler(String uid, Map<String, Object> user) throws FirebaseAuthException;
}
