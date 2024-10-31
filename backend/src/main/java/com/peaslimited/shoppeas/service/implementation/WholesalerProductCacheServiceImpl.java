package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

@Service
public class WholesalerProductCacheServiceImpl {

    @Autowired
    private Firestore firestore;

    private final Set<String> validSWPIDs = new HashSet<>();
    private ListenerRegistration listenerRegistration;

    @PostConstruct
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
    }

    // Method to check if a TID exists in the cache
    public boolean doesTransactionExist(String swp_id) {
        return validSWPIDs.contains(swp_id);
    }

    // Clean up the listener when the application shuts down
    @PreDestroy
    public void removeListener() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
}
