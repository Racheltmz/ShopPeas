package com.peaslimited.shoppeas.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private final String uid; // The UID from Firebase
    private final Object credentials;

    public FirebaseAuthenticationToken(String uid, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.uid = uid;
        this.credentials = null;
        setAuthenticated(true); // By default, set it to authenticated
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public String getPrincipal() {
        return uid; // Returning the UID as the principal
    }

    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        super.setAuthenticated(authenticated);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}

