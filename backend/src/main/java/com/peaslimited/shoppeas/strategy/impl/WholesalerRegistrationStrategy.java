package com.peaslimited.shoppeas.strategy.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.service.AuthService;
import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class WholesalerRegistrationStrategy implements RegistrationStrategy {

    @Autowired
    private AuthService authService;

    @Override
    public void register(String uid, Map<String, Object> wholesalerData) throws IOException, FirebaseAuthException {
        authService.registerWholesaler(uid, wholesalerData);
    }
}