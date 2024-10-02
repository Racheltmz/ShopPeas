package com.peaslimited.shoppeas.controller;

import com.google.api.gax.rpc.UnauthenticatedException;
import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // @saffron registerConsumer

    @PostMapping("/wholesaler")
    @PreAuthorize("hasRole('CONSUMER', 'WHOLESALER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String registerWholesaler(@RequestBody Map<String, Object> wholesaler) throws IOException, FirebaseAuthException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        return userService.registerWholesaler(uid, wholesaler);
    }
}
