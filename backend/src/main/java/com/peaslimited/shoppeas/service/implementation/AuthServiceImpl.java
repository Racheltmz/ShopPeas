package com.peaslimited.shoppeas.service.implementation;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.factory.RegistrationFactory;
import com.peaslimited.shoppeas.repository.AuthRepository;
import com.peaslimited.shoppeas.service.*;
import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RegistrationFactory registrationFactory;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public void registerConsumer(String UID, Map<String, Object> user) throws FirebaseAuthException, IOException {
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("consumer");
        strategy.register(UID, user);
    }

    @Override
    public void registerWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException, IOException {
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("wholesaler");
        strategy.register(UID, user);
    }

    @Override
    public void updateConsumer(String UID, Map<String, Object> user) throws FirebaseAuthException {
        // This can remain as is or can be incorporated into the strategy if you need specific update strategies.
        authRepository.updateConsumer(UID, user);
    }

    @Override
    public void updateWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException {
        // This can remain as is or can be incorporated into the strategy if you need specific update strategies.
        authRepository.updateWholesaler(UID, user);
    }
}

