package com.peaslimited.shoppeas.strategy.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;
import com.peaslimited.shoppeas.repository.AuthRepository;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import com.peaslimited.shoppeas.service.WholesalerService;
import com.peaslimited.shoppeas.strategy.RegistrationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Component
public class WholesalerRegistrationStrategy implements RegistrationStrategy {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private WholesalerAddressService wholesalerAddressService;

    @Autowired
    private WholesalerAccountService wholesalerAccountService;

    @Override
    public void register(String uid, Map<String, Object> userData) throws FirebaseAuthException {
        // Create and add wholesaler
        WholesalerDTO wholesaler = createWholesalerFromMap(userData);
        // Set custom claims
        authRepository.setUserClaims(uid, Map.of("wholesaler", true));
        wholesalerService.addWholesaler(uid, wholesaler);

        // Create and add wholesaler account
        WholesalerAccountDTO wholesalerAccount = createWholesalerAccountFromMap(userData);
        wholesalerAccountService.addWholesalerAccount(userData.get("uen").toString(), wholesalerAccount);

        // Create and add wholesaler address
        WholesalerAddressDTO wholesalerAddress = createWholesalerAddressFromMap(userData);
        wholesalerAddressService.addWholesalerAddress(userData.get("uen").toString(), wholesalerAddress);
    }

    private WholesalerDTO createWholesalerFromMap(Map<String, Object> user) {
        return new WholesalerDTO(
                user.get("uen").toString(),
                user.get("name").toString(),
                user.get("email").toString(),
                user.get("phone_number").toString(),
                user.get("currency").toString(),
                0.0, // rating
                new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0)) // numRatingsList
        );
    }

    private WholesalerAccountDTO createWholesalerAccountFromMap(Map<String, Object> user) {
        return new WholesalerAccountDTO(
                user.get("bank").toString(),
                user.get("bank_account_name").toString(),
                user.get("bank_account_no").toString()
        );
    }

    private WholesalerAddressDTO createWholesalerAddressFromMap(Map<String, Object> user) {
        return new WholesalerAddressDTO(
                user.get("street_name").toString(),
                user.get("unit_no") != null ? user.get("unit_no").toString() : null,
                user.get("building_name") != null ? user.get("building_name").toString() : null,
                user.get("city").toString(),
                user.get("postal_code").toString()
        );
    }
}
