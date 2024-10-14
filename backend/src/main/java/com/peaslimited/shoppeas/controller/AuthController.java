package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.service.AuthService;
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
    private AuthService authService;

    @PostMapping("/consumer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerConsumer(@RequestBody Map<String, Object> consumer) throws IOException, FirebaseAuthException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        authService.registerConsumer(uid, consumer);
    }

    @PostMapping("/wholesaler")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerWholesaler(@RequestBody Map<String, Object> wholesaler) throws IOException, FirebaseAuthException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        authService.registerWholesaler(uid, wholesaler);
    }
}
