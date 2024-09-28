package com.peaslimited.shoppeas.repository;

import com.google.firebase.auth.FirebaseAuthException;

import java.util.Map;

public interface AuthRepository {

    String createUser(Map<String, Object> user) throws FirebaseAuthException;

    void setUserClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException;

    void updateUser(String uid, Map<String, Object> user) throws FirebaseAuthException;

}
