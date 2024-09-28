package com.peaslimited.shoppeas.service;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.Map;

public interface AuthService {
    // @saffron this function for creating consumers
    // void registerConsumer();

    String registerWholesaler(Map<String, Object> wholesaler) throws FirebaseAuthException, IOException;

    void updateWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException;

}
