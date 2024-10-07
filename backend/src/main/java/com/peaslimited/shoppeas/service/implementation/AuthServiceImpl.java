package com.peaslimited.shoppeas.service.implementation;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.repository.AuthRepository;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @Autowired
    private ConsumerAccountService consumerAccountService;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private WholesalerAddressService wholesalerAddressService;

    @Autowired
    private WholesalerAccountService wholesalerAccountService;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public void registerConsumer(String UID, Map<String, Object> user) throws FirebaseAuthException {
        // Create and add consumer
        ConsumerDTO consumer = createConsumerFromMap(user);
        consumerService.addConsumer(UID, consumer);

        // Create and add wholesaler account
        ConsumerAccountDTO consumerAccount = createConsumerAccountFromMap(user);
        consumerAccountService.addConsumerAccount(UID, consumerAccount);

        // Create and add wholesaler address
        ConsumerAddressDTO consumerAddress = createConsumerAddressFromMap(user);
        consumerAddressService.addConsumerAddress(UID, consumerAddress);
    }

    @Override
    public void updateConsumer(String UID, Map<String, Object> user) throws FirebaseAuthException {
        authRepository.updateConsumer(UID, user);
    }

    @Override
    public void registerWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException {
        // Get uen
        String UEN = user.get("uen").toString();
        // Create and add wholesaler
        WholesalerDTO wholesaler = createWholesalerFromMap(user);
        wholesalerService.addWholesaler(UID, wholesaler);

        // Create and add wholesaler account
        WholesalerAccountDTO wholesalerAccount = createWholesalerAccountFromMap(user);
        wholesalerAccountService.addWholesalerAccount(UEN, wholesalerAccount);

        // Create and add wholesaler address
        WholesalerAddressDTO wholesalerAddress = createWholesalerAddressFromMap(user);
        wholesalerAddressService.addWholesalerAddress(UEN, wholesalerAddress);
    }

    @Override
    public void updateWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException {
        authRepository.updateWholesaler(UID, user);
    }


    private ConsumerDTO createConsumerFromMap(Map<String, Object> user) {
        return new ConsumerDTO(
            user.get("first_name").toString(),
            user.get("last_name").toString(),
            user.get("email").toString(),
            user.get("phone_number").toString()
        );
    }

    private ConsumerAccountDTO createConsumerAccountFromMap(Map<String, Object> user) {
        return new ConsumerAccountDTO(
            Long.valueOf(user.get("card_no").toString()),
            user.get("expiry_date").toString(),
            Integer.valueOf(user.get("cvv").toString()),
            user.get("name").toString()
        );
    }

    private ConsumerAddressDTO createConsumerAddressFromMap(Map<String, Object> user) {
        // Check for null fields
        Object unit_no_obj = user.get("unit_no");
        Object building_name_obj = user.get("building_name");
        String unit_no = null;
        String building_name = null;

        // Only extract string if not null
        if (unit_no_obj != null) {
            unit_no = unit_no_obj.toString();
        }
        if (building_name_obj != null) {
            building_name = building_name_obj.toString();
        }

        return new ConsumerAddressDTO(
            user.get("street_name").toString(),
            unit_no,
            building_name,
            user.get("city").toString(),
            Integer.valueOf(user.get("postal_code").toString())
        );
    }

    private WholesalerDTO createWholesalerFromMap(Map<String, Object> user) {
        // Initialise ratings to 0
        double rating = 0.0;
        ArrayList<Integer> numRatingsList = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));

        return new WholesalerDTO(
            user.get("uen").toString(),
            user.get("name").toString(),
            user.get("email").toString(),
            user.get("phone_number").toString(),
            user.get("currency").toString(),
            rating,
            numRatingsList
        );
    }

    private WholesalerAccountDTO createWholesalerAccountFromMap(Map<String, Object> user) {
        return new WholesalerAccountDTO(
            user.get("bank").toString(),
            user.get("bank_account_name").toString(),
            Long.valueOf(user.get("bank_account_no").toString())
        );
    }

    private WholesalerAddressDTO createWholesalerAddressFromMap(Map<String, Object> user) {
        // Check for null fields
        Object unit_no_obj = user.get("unit_no");
        Object building_name_obj = user.get("building_name");
        String unit_no = null;
        String building_name = null;

        // Only extract string if not null
        if (unit_no_obj != null) {
            unit_no = unit_no_obj.toString();
        }
        if (building_name_obj != null) {
            building_name = building_name_obj.toString();
        }

        return new WholesalerAddressDTO(
            user.get("street_name").toString(),
            unit_no,
            building_name,
            user.get("city").toString(),
            Integer.valueOf(user.get("postal_code").toString())
        );
    }

}
