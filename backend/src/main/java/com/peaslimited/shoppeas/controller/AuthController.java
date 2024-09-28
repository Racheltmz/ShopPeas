package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthService authService;

    // @saffron registerConsumer

    @PostMapping
    @RequestMapping(value = "/wholesaler")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String registerWholesaler(@RequestBody Map<String, Object> wholesaler) throws IOException, FirebaseAuthException {
        return authService.registerWholesaler(wholesaler);
    }
}
