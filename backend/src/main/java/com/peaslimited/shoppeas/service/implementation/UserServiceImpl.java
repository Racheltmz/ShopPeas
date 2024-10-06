package com.peaslimited.shoppeas.service.implementation;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.repository.UserRepository;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private WholesalerAddressService wholesalerAddressService;

    @Autowired
    private WholesalerAccountService wholesalerAccountService;

    @Autowired
    private UserRepository authRepository;

    // @saffron registerConsumer

    @Override
    public String registerWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException {
//        // Create Firebase user
//        String uid = authRepository.createUser(user);
//
//        // Set user privilege
//        authRepository.setUserClaims(uid, Map.of("user", true));

        String uen = user.get("uen").toString();
        // Create and add wholesaler
        Wholesaler wholesaler = createWholesalerFromMap(user);
        wholesalerService.addWholesaler(UID, wholesaler);

        // Create and add wholesaler account
        WholesalerAccountDTO wholesalerAccount = createWholesalerAccountFromMap(user);
        wholesalerAccountService.addWholesalerAccount(uen, wholesalerAccount);

        // Create and add wholesaler address
        WholesalerAddressDTO wholesalerAddress = createWholesalerAddressFromMap(user);
        wholesalerAddressService.addWholesalerAddress(uen, wholesalerAddress);

        return UID;
    }

    private Wholesaler createWholesalerFromMap(Map<String, Object> user) {
        return new Wholesaler(
            user.get("uen").toString(),
            user.get("name").toString(),
            user.get("email").toString(),
            user.get("phone_number").toString(),
            user.get("currency").toString(),
            Double.valueOf(user.get("rating").toString()),
            Integer.valueOf(user.get("num_ratings").toString())
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
        return new WholesalerAddressDTO(
            user.get("street_name").toString(),
            user.get("unit_no").toString(),
            user.get("building_name").toString(),
            user.get("city").toString(),
            Integer.valueOf(user.get("postal_code").toString())
        );
    }

    @Override
    public void updateWholesaler(String UID, Map<String, Object> user) throws FirebaseAuthException {
        authRepository.updateUser(UID, user);
    }
}
