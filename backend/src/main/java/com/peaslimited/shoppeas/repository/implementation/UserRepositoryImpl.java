package com.peaslimited.shoppeas.repository.implementation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.peaslimited.shoppeas.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

//    public String createUser(Map<String, Object> user) throws FirebaseAuthException {
//        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
//                .setEmail(user.get("email").toString())
//                .setEmailVerified(false)
//                .setPassword(user.get("password").toString())
//                .setPhoneNumber(user.get("phone_number").toString())
//                .setDisplayName(user.get("name").toString())
//                .setDisabled(false);
//
//        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
//        return userRecord.getUid();
//    }
//
//    public void setUserClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException {
//        FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
//    }

    public void updateUser(String uid, Map<String, Object> user) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                .setEmail(user.get("email").toString())
                .setPhoneNumber(user.get("phone_number").toString())
                .setDisplayName(user.get("name").toString());
        FirebaseAuth.getInstance().updateUser(request);
    }

}
