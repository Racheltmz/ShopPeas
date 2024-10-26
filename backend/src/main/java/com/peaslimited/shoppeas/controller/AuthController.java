package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.factory.RegistrationFactory;
import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RegistrationFactory registrationFactory;

    @PostMapping("/consumer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerConsumer(@RequestBody Map<String, Object> consumer) throws IOException, FirebaseAuthException {
        String uid = getUid();
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("consumer");
        strategy.register(uid, consumer);
    }

    @PostMapping("/wholesaler")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerWholesaler(@RequestBody Map<String, Object> wholesaler) throws IOException, FirebaseAuthException {
        String uid = getUid();
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("wholesaler");
        strategy.register(uid, wholesaler);
    }

    private String getUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}