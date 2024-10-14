package com.peaslimited.shoppeas.repository.implementation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.peaslimited.shoppeas.repository.AuthRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

    public void setUserClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException {
        FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
    }

    public void updateConsumer(String uid, Map<String, Object> user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(user.get("email").toString())
                .setPhoneNumber(user.get("phone_number").toString())
                .setDisplayName(user.get("first_name").toString() + " " + user.get("last_name").toString());
        FirebaseAuth.getInstance().updateUser(request);
    }

    public void updateWholesaler(String uid, Map<String, Object> user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(user.get("email").toString())
                .setPhoneNumber(user.get("phone_number").toString())
                .setDisplayName(user.get("name").toString());
        FirebaseAuth.getInstance().updateUser(request);
    }
}
