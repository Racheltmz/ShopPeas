package com.peaslimited.shoppeas.service.implementation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.model.WholesalerAccount;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.service.AuthService;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import com.peaslimited.shoppeas.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    WholesalerService wholesalerService;

    @Autowired
    WholesalerAddressService wholesalerAddressService;

    @Autowired
    WholesalerAccountService wholesalerAccountService;

    @Override
    public void addWholesaler(Map<String, Object> user) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.get("email").toString())
                .setEmailVerified(false)
                .setPassword(user.get("password").toString())
                .setPhoneNumber(user.get("phone_number").toString())
                .setDisplayName(user.get("name").toString())
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        String uid = userRecord.getUid();

        // Set user privilege on the user corresponding to uid
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", true);
        FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);

        String uen = user.get("uen").toString();
        // Add wholesaler record
        Wholesaler wholesaler = new Wholesaler(
                uen,
                user.get("name").toString(),
                user.get("email").toString(),
                user.get("phone_number").toString(),
                user.get("currency").toString(),
                Double.valueOf(user.get("rating").toString()),
                Integer.valueOf(user.get("num_ratings").toString())
        );

        wholesalerService.addWholesaler(uid, wholesaler);

        // Add wholesaler account record
        WholesalerAccount wholesalerAccount = new WholesalerAccount(
                user.get("bank").toString(),
                user.get("bank_account_name").toString(),
                Long.valueOf(user.get("bank_account_no").toString())
        );

        wholesalerAccountService.addWholesalerAccount(uen, wholesalerAccount);

        // Add wholesaler address record
        WholesalerAddress wholesalerAddress = new WholesalerAddress(
                user.get("street_name").toString(),
                user.get("unit_no").toString(),
                user.get("building_name").toString(),
                user.get("city").toString(),
                Integer.valueOf(user.get("postal_code").toString())
        );

        wholesalerAddressService.addWholesalerAddress(uen, wholesalerAddress);
        // HANDLE 400 BAD REQUEST EMAIL EXISTS
    }
}
