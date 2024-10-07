package com.peaslimited.shoppeas.service;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.Map;

public interface AuthService {
    void registerConsumer(String UID, Map<String, Object> consumer) throws FirebaseAuthException, IOException;

    void updateConsumer(String UID, Map<String, Object> consumer) throws FirebaseAuthException;

    void registerWholesaler(String UID, Map<String, Object> wholesaler) throws FirebaseAuthException, IOException;

    void updateWholesaler(String UID, Map<String, Object> wholesaler) throws FirebaseAuthException;
}
