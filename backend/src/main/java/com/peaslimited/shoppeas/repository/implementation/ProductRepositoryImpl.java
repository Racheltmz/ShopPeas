package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final String COLLECTION = "products";
    @Autowired
    private Firestore firestore;

    // get product details
    @Override
    public ProductDTO findByPID(String PID) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("products").document(PID);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Product object
        ProductDTO product = null;
        if (document.exists()) {
            product = document.toObject(ProductDTO.class);
        }
        return product;

    }

    @Override
    public List<Product> findAll() throws ExecutionException, InterruptedException {
        // Fetch all products from firebase
        QuerySnapshot snapshot = firestore.collection("products").get().get();

        // Map each firebase document to a product object
        return snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(Product.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addByPID(String PID, ProductDTO product) {
        firestore.collection("products").document(PID).set(product);
    }

    @Override
    public void updateByPID(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection("products").document(PID);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }

}
