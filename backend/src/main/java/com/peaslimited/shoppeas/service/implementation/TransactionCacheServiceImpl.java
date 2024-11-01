package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
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
public class TransactionCacheServiceImpl {

    @Autowired
    private Firestore firestore;

    private final Set<String> validTIDs = new HashSet<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void initializeScheduledCacheUpdate() {
        // Schedule periodic updates to the cache every 3 hours
        scheduler.scheduleAtFixedRate(this::updateCache, 0, 3, TimeUnit.HOURS);
    }

    private void updateCache() {
        try {
            QuerySnapshot snapshot = firestore.collection("transactions").get().get();

            synchronized (validTIDs) {
                validTIDs.clear();
                for (QueryDocumentSnapshot document : snapshot) {
                    validTIDs.add(document.getId());
                }
            }

            System.out.println("Cache updated every 3 hours with transaction IDs.");
        } catch (Exception e) {
            System.err.println("Failed to update cache: " + e);
        }
    }

    // Method to check if a TID exists in the cache
    public boolean doesTransactionExist(String tid) {
        synchronized (validTIDs) {
            return validTIDs.contains(tid);
        }
    }

    // Clean up the scheduler when the application shuts down
    @PreDestroy
    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}