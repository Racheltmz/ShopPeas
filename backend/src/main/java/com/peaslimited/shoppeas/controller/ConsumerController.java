package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.ConsumerAddressDTO;
import com.peaslimited.shoppeas.dto.ConsumerDTO;
import com.peaslimited.shoppeas.dto.ConsumerProfileDTO;
import com.peaslimited.shoppeas.dto.mapper.ConsumerProfileMapper;
import com.peaslimited.shoppeas.service.AuthService;
import com.peaslimited.shoppeas.service.ConsumerAccountService;
import com.peaslimited.shoppeas.service.ConsumerAddressService;
import com.peaslimited.shoppeas.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @Autowired
    private ConsumerAccountService consumerAccountService;

    /**
     * Get consumer details by UID
     * @return consumer details
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public ConsumerProfileDTO viewConsumer() throws ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        ConsumerDTO consumer = consumerService.getConsumer(uid);
        ConsumerAddressDTO consumerAddress = consumerAddressService.getConsumerAddress(uid);

        // Invoke mapper to combine information and return the profile DTO
        return ConsumerProfileMapper.toProfileDTO(consumer, consumerAddress);
    }

    @PatchMapping("/profile/update")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateConsumer(@RequestBody Map<String, Map<String, Object>> data) throws ExecutionException, InterruptedException, FirebaseAuthException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        Map<String, Object> profileDetails = data.get("consumer");
        Map<String, Object> accountDetails = data.get("consumerAccount");
        Map<String, Object> addressDetails = data.get("consumerAddress");
        authService.updateConsumer(uid, profileDetails);
        consumerService.updateConsumer(uid, profileDetails);
        consumerAccountService.updateConsumerAccount(uid, accountDetails);
        consumerAddressService.updateConsumerAddress(uid, addressDetails);
    }
}
