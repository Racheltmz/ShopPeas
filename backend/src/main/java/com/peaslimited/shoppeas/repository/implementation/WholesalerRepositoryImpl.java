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
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Implementation of WholesalerRepository for performing operations on wholesaler data in Firestore,
 * including methods to retrieve, add, update, and manage wholesaler information.
 */
@Repository
public class WholesalerRepositoryImpl implements WholesalerRepository {

    private final String COLLECTION = "wholesaler";

    @Autowired
    private Firestore firestore;

    /**
     * {@inheritDoc}
     *
     * @param UID the unique identifier of the wholesaler
     * @return a {@link WholesalerDTO} containing wholesaler details, or null if the document does not exist
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link DocumentSnapshot} of the wholesaler document, or null if no document matches
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * @param name the name of the wholesaler
     * @return a {@link DocumentSnapshot} of the wholesaler document, or null if no document matches
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public DocumentSnapshot findDocByWholesalerName(String name) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("name", name).get();

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

    /**
     * {@inheritDoc}
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link WholesalerDTO} containing wholesaler details, or null if the document does not exist
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public WholesalerDTO findByUEN(String UEN) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = findDocByUEN(UEN);
        return document.toObject(WholesalerDTO.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param uen the unique entity number of the wholesaler
     * @return the name of the wholesaler as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public String findWholesalerName(String uen) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = firestore.collection(COLLECTION)
                .whereEqualTo("uen", uen)
                .limit(1)
                .get()
                .get();

        return Objects.requireNonNull(querySnapshot.getDocuments().getFirst().get("name")).toString();
    }

    /**
     * {@inheritDoc}
     *
     * @param uen_list a list of UENs for the wholesalers
     * @return a list of {@link WholesalerDTO} objects containing details of each wholesaler in the specified order
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    @Override
    public List<WholesalerDTO> findWholesalers(List<String> uen_list) throws ExecutionException, InterruptedException {
        CollectionReference wholesalerCollection = firestore.collection(COLLECTION);
        Query wholesalerQuery = wholesalerCollection.whereIn("uen", uen_list);
        QuerySnapshot productSnapshot = wholesalerQuery.get().get();
    
        // Map documents by UEN
        Map<String, WholesalerDTO> wholesalerMap = productSnapshot.getDocuments().stream()
                .collect(Collectors.toMap(
                    doc -> doc.getString("uen"),
                    doc -> doc.toObject(WholesalerDTO.class)
                ));
    
        // Return the result in the order of uen_list
        return uen_list.stream()
                .map(wholesalerMap::get)
                .filter(Objects::nonNull) // in case a UEN didn't match any document
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * @param UID the unique identifier of the wholesaler
     * @param wholesaler a {@link WholesalerDTO} object containing the wholesaler's details
     */
    @Override
    public void addByUID(String UID, WholesalerDTO wholesaler) {
        firestore.collection(COLLECTION).document(UID).set(wholesaler);
    }

    /**
     * {@inheritDoc}
     *
     * @param UID the unique identifier of the wholesaler
     * @param data a {@link Map} containing the fields and values to update
     * @return the updated UID as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException
     */
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

    /**
     * {@inheritDoc}
     *
     * @param UEN the unique entity number of the wholesaler
     * @return a {@link RatingDTO} containing the wholesaler's rating details
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    @Override
    public RatingDTO findRatingByUEN(String UEN) throws ExecutionException, InterruptedException {
        WholesalerDTO wholesaler = findByUEN(UEN);
        return new RatingDTO(wholesaler.getRating(), wholesaler.getNum_ratings());
    }

    /**
     * {@inheritDoc}
     *
     * @param UEN the unique entity number of the wholesaler
     * @param rating the new rating to set
     * @throws ExecutionException
     * @throws InterruptedException 
     */
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
