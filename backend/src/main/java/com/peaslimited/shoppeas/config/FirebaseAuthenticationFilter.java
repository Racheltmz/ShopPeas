package com.peaslimited.shoppeas.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.peaslimited.shoppeas.exception.ApiExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LogManager.getLogger(ApiExceptionHandler.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String uid = authorizationHeader.substring(7);  // Extract the token

            UserRecord user = null;
            try {
                user = FirebaseAuth.getInstance().getUser(uid);
                // Extract custom claims (roles)
                String role = null;

                if (Boolean.TRUE.equals(user.getCustomClaims().get("consumer"))) {
                    role = "CONSUMER";
                } else if (Boolean.TRUE.equals(user.getCustomClaims().get("wholesaler"))) {
                    role = "WHOLESALER";
                }
                // Set authentication with the user's role
                FirebaseAuthenticationToken authentication = new FirebaseAuthenticationToken(
                        uid, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (FirebaseAuthException exception) {
                // Response Status
                HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
                // Log error
                log.error("{} Unauthenticated access: {}", unauthorized, exception.getMessage());
            }
        }
        chain.doFilter(request, response);  // Continue with the next filter
    }
}

