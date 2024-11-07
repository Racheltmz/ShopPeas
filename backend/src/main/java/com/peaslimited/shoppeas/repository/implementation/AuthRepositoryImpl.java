package com.peaslimited.shoppeas.repository.implementation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.peaslimited.shoppeas.repository.AuthRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Implementation of AuthRepository for managing Firebase authentication and authorization,
 * including methods to set custom user claims and update user information in Firebase.
 */
@Repository
public class AuthRepositoryImpl implements AuthRepository {

    /**
     * {@inheritDoc}
     * 
     * Sets custom claims for a specified user in Firebase to manage access control.
     *
     * @param uid the unique identifier of the user
     * @param claims a {@link Map} of claim names to values, representing the custom claims to set for the user
     * @throws FirebaseAuthException
     */
    public void setUserClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException {
        FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
    }

    /**
     * {@inheritDoc}
     *
     * Updates information for a consumer user in Firebase Authentication, 
     * including phone number and display name (constructed from first and last names).
     *
     * @param uid the unique identifier of the consumer
     * @param user a {@link Map} containing fields to update and their respective values
     * @throws FirebaseAuthException
     */
    public void updateConsumer(String uid, Map<String, Object> user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setPhoneNumber(user.get("phone_number").toString())
                .setDisplayName(user.get("first_name").toString() + " " + user.get("last_name").toString());
        FirebaseAuth.getInstance().updateUser(request);
    }

    /**
     * {@inheritDoc}
     *
     * Updates information for a wholesaler user in Firebase Authentication,
     * including phone number and display name.
     *
     * @param uid the unique identifier of the wholesaler
     * @param user a {@link Map} containing fields to update and their respective values
     * @throws FirebaseAuthException
     */
    public void updateWholesaler(String uid, Map<String, Object> user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setPhoneNumber(user.get("phone_number").toString())
                .setDisplayName(user.get("name").toString());
        FirebaseAuth.getInstance().updateUser(request);
    }
}
