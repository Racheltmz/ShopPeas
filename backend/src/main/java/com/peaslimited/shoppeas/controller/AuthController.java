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

/**
 * AuthController handles the registration of users.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private RegistrationFactory registrationFactory;

    /**
     * Registers a consumer when a user signs up as a consumer and is called from the frontend with
     * HTTP path "/auth/consumer".
     * @param consumer Map<String, Object> containing the data needed to register a consumer.
     * @throws IOException
     * @throws FirebaseAuthException
     */
    @PostMapping("/consumer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerConsumer(@RequestBody Map<String, Object> consumer) throws IOException, FirebaseAuthException {
        String uid = getUid();
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("consumer");
        strategy.register(uid, consumer);
    }

    /**
     * Registers a wholesaler when a user signs up as a business and is called from the frontend with
     * HTTP path "/auth/wholesaler".
     * @param wholesaler Map<String, Object> containing the data needed to register a wholesaler/ business.
     * @throws IOException
     * @throws FirebaseAuthException
     */
    @PostMapping("/wholesaler")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerWholesaler(@RequestBody Map<String, Object> wholesaler) throws IOException, FirebaseAuthException {
        String uid = getUid();
        RegistrationStrategy strategy = registrationFactory.getRegistrationStrategy("wholesaler");
        strategy.register(uid, wholesaler);
    }

    /**
     * Gets a user's ID (uid) from FireBase (document ID).
     * @return A String containing the uid.
     */
    private String getUid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}