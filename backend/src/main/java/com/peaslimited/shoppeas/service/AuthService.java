package com.peaslimited.shoppeas.service;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.Map;

public interface AuthService {
//    void addConsumer();

    void addWholesaler(Map<String, Object> wholesaler) throws FirebaseAuthException, IOException;
}
