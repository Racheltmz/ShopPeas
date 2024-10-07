package com.peaslimited.shoppeas.repository;

import com.google.firebase.auth.FirebaseAuthException;

import java.util.Map;

public interface AuthRepository {

    void updateConsumer(String uid, Map<String, Object> user) throws FirebaseAuthException;

    void updateWholesaler(String uid, Map<String, Object> user) throws FirebaseAuthException;
}
