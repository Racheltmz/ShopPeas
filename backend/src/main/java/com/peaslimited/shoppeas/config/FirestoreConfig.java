package com.peaslimited.shoppeas.config;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Get firestore
 */
@Configuration
public class FirestoreConfig {

    /**
     * Configuration to get firestore using firestore client
     */
    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}
