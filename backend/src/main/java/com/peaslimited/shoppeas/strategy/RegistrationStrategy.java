package com.peaslimited.shoppeas.strategy;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.Map;

public interface RegistrationStrategy {
    void register(String uid, Map<String, Object> userData) throws IOException, FirebaseAuthException;
}