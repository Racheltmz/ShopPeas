package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.RatingDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class WholesalerRepositoryImpl implements WholesalerRepository {

    private final String COLLECTION = "wholesaler";

    @Autowired
    private Firestore firestore;

    // Get wholesaler details
    @Override
    public WholesalerDTO findByUID(String UID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        WholesalerDTO wholesaler = null;
        if (document.exists()) {
            wholesaler = document.toObject(WholesalerDTO.class);
        }
        return wholesaler;
    }

    @Override
    public DocumentSnapshot findDocByUEN(String UEN) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uen", UEN).get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();

        // Convert document to Wholesaler object
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        // Check if any documents match
        DocumentSnapshot document = null;
        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            document = documents.getFirst();
        }

        return document;
    }

    @Override
    public WholesalerDTO findByUEN(String UEN) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = findDocByUEN(UEN);
        return document.toObject(WholesalerDTO.class);
    }


    @Override
    public void addByUID(String UID, WholesalerDTO wholesaler) {
        firestore.collection(COLLECTION).document(UID).set(wholesaler);
    }

    @Override
    public String updateByUID(String UID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(UID);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
        return findByUID(UID).getUEN();
    }

    @Override
    public RatingDTO findRatingByUEN(String UEN) throws ExecutionException, InterruptedException {
        WholesalerDTO wholesaler = findByUEN(UEN);
        return new RatingDTO(wholesaler.getRating(), wholesaler.getNum_ratings());
    }

    @Override
    public void updateRatingByUEN(String UEN, Integer rating) throws ExecutionException, InterruptedException {
        DocumentReference docRef = findDocByUEN(UEN).getReference();
        WholesalerDTO wholesaler = findByUEN(UEN);

        // Get current rating details
        ArrayList<Integer> cur_num_ratings = wholesaler.getNum_ratings();

        // Update ratings count
        cur_num_ratings.set(rating-1, cur_num_ratings.get(rating-1) + 1);

        int total_rates = 0;
        double total_rating = 0.0;
        for(int score = 0; score < cur_num_ratings.size(); score++) {
            int num_rates = cur_num_ratings.get(score);
            total_rates += num_rates;
            total_rating += num_rates * (score + 1);
        }
        double new_rating = total_rating / total_rates;

        docRef.update("rating", new_rating);
        docRef.update("num_ratings", cur_num_ratings);

    }
}
