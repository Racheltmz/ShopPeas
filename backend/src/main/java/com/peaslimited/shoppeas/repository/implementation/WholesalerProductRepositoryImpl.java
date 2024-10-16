package com.peaslimited.shoppeas.repository.implementation;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class WholesalerProductRepositoryImpl implements WholesalerProductRepository {

    private final String COLLECTION = "wholesaler_products";

    @Autowired
    private Firestore firestore;

    @Override
    // Fetch wholesaler products by uen
    public List<WholesalerProducts> findByUEN(String uen) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection(COLLECTION).whereEqualTo("uen", uen).whereEqualTo("active", true).get().get();

        return snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .collect(Collectors.toList());
    }

    // Fetch products by their PID
    @Override
    public List<WholesalerProductDTO> findByPid(String pid) throws ExecutionException, InterruptedException {
        // Query Firestore to get all wholesaler products with the given PID
        QuerySnapshot snapshot = firestore.collection(COLLECTION)
                .whereEqualTo("pid", pid)
                .whereEqualTo("active", true)
                .get().get();

        // Map each matching document to a WholesalerProductDTO object
        return snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProductDTO.class))
                .collect(Collectors.toList());
    }


    // Add a new wholesaler product
    @Override
    public void addWholesalerProduct(WholesalerProductDTO wholesalerProductDTO) {
        firestore.collection(COLLECTION).add(wholesalerProductDTO); // Firebase generates ID
    }

    @Override
    public void updateWholesalerProduct(String swpid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(swpid);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }

    @Override
    public void deleteWholesalerProduct(String swpid) {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(swpid);
        docRef.update("active", false);
    }

}
