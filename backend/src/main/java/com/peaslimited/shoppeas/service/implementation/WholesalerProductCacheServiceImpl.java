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

    /*@PostConstruct
    public void initializeListener() {
        listenerRegistration = firestore.collection("wholesaler_products")
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        System.out.println("Listen failed: " + e);
                        return;
                    }

                    // Update the cache based on document changes
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                validSWPIDs.add(dc.getDocument().getId());
                                break;
                            case REMOVED:
                                validSWPIDs.remove(dc.getDocument().getId());
                                break;
                            case MODIFIED:
                                // Usually, modifications don't affect IDs, so no action needed
                                break;
                        }
                    }
                });
    }*/

    // Method to check if a TID exists in the cache
    /*public boolean doesTransactionExist(String swp_id) {
        return validSWPIDs.contains(swp_id);
    }*/
    public boolean doesTransactionExist(String tid) {
        synchronized (validSWPIDs) {
            return validSWPIDs.contains(tid);
        }
    }

    // Clean up the listener when the application shuts down
    /*@PreDestroy
    public void removeListener() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }*/
    @PreDestroy
    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
