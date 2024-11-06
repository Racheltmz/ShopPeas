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

/**
 * Strategy implementation for registering a new wholesaler in the system.
 * This class handles the process of adding a new wholesaler, their account,
 * and their address to the database. It also sets custom claims for the
 * newly registered user in Firebase Authentication.
 */
@Component
public class WholesalerRegistrationStrategy implements RegistrationStrategy {

    /**
     * Repository for interacting with Firebase Authentication.
     */
    @Autowired
    private AuthRepository authRepository;

    /**
     * Service responsible for managing wholesaler data in the system.
     */
    @Autowired
    private WholesalerService wholesalerService;

    /**
     * Service responsible for managing wholesaler address data in the system.
     */
    @Autowired
    private WholesalerAddressService wholesalerAddressService;

    /**
     * Service responsible for managing wholesaler account data in the system.
     * Used to add the wholesaler's account details to the database.
     */
    @Autowired
    private WholesalerAccountService wholesalerAccountService;

    /**
     * Registers a new wholesaler by creating their profile, account, and address in the system.
     * This includes adding wholesaler details, setting custom claims in Firebase,
     * and adding the wholesaler's account and address to the system.
     * @param uid The unique user ID of the wholesaler in Firebase Authentication.
     * @param userData A map containing the user data, including wholesaler details, account, and address.
     * @throws FirebaseAuthException If there is an issue setting user claims in Firebase Authentication.
     */
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

    /**
     * Creates a {@link WholesalerDTO} object from the provided user data map.
     * @param user A map containing the wholesaler data.
     * @return A `WholesalerDTO` containing the wholesaler's profile information.
     */
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

    /**
     * Creates a {@link WholesalerAccountDTO} object from the provided user data map.
     * @param user A map containing the wholesaler account data.
     * @return A `WholesalerAccountDTO` containing the wholesaler's account details.
     */
    private WholesalerAccountDTO createWholesalerAccountFromMap(Map<String, Object> user) {
        return new WholesalerAccountDTO(
                user.get("bank").toString(),
                user.get("bank_account_name").toString(),
                user.get("bank_account_no").toString()
        );
    }

    /**
     * Creates a {@link WholesalerAddressDTO} object from the provided user data map.
     * @param user A map containing the wholesaler address data.
     * @return A `WholesalerAddressDTO` containing the wholesaler's address details.
     */
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
