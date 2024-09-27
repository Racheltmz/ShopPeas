package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.JsonObject;
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
    AuthService authService;

    @PostMapping
    @RequestMapping(value = "/wholesaler")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addWholesaler(@RequestBody Map<String, Object> wholesaler) throws IOException, FirebaseAuthException {
        authService.addWholesaler(wholesaler);
    }
}
