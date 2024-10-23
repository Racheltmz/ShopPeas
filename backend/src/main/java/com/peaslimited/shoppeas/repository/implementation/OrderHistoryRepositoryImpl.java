package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.OrderHistoryDTO;
import com.peaslimited.shoppeas.repository.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final String COLLECTION = "order_history";

    @Autowired
    private Firestore firestore;

    @Override
    public ArrayList<OrderHistoryDTO> getOrderHistoryByUID(String uid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(uid);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        return null;
    }

}
