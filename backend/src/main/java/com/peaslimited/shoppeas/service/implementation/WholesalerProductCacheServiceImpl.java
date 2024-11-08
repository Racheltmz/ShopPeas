package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Handles operations related to wholesaler product cache functionality.
 * This service updates cache about wholesaler product information.
 */
@Service
public class WholesalerProductCacheServiceImpl {

    @Autowired
    private Firestore firestore;

    private final Set<String> validSWPIDs = new HashSet<>();
    //private ListenerRegistration listenerRegistration;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void initializeScheduledCacheUpdate() {
        // Schedule periodic updates to the cache every 3 hours
        scheduler.scheduleAtFixedRate(this::updateCache, 0, 3, TimeUnit.HOURS);
    }

    private void updateCache() {
        try {
            QuerySnapshot snapshot = firestore.collection("wholesaler_products").get().get();

            synchronized (validSWPIDs) {
                validSWPIDs.clear();
                for (QueryDocumentSnapshot document : snapshot) {
                    validSWPIDs.add(document.getId());
                }
            }

            System.out.println("Cache updated every 3 hours with transaction IDs.");
        } catch (Exception e) {
            System.err.println("Failed to update cache: " + e);
        }
    }

    /**
     * Check if transaction exists for given TID.
     * @param tid Transaction ID.
     * @return boolean value of whether the transaction exists.
     */
    public boolean doesTransactionExist(String tid) {
        synchronized (validSWPIDs) {
            return validSWPIDs.contains(tid);
        }
    }

    @PreDestroy
    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
