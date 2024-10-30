package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final String COLLECTION = "products";

    @Autowired
    private Firestore firestore;

    // get product details
    @Override
    public ProductDTO findByPID(String pid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(pid);

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
        QuerySnapshot snapshot = firestore.collection(COLLECTION).get().get();

        // Map each firebase document to a product object
        return snapshot.getDocuments().stream()
                .map(doc -> {
                    Product product = doc.toObject(Product.class);
                    product.setPid(doc.getId());
                    return product;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDetailedDTO> findProductDetails(List<String> swpid_list, List<String> productid_list, List<WholesalerProducts> wholesaler_products) throws ExecutionException, InterruptedException {
        List<DocumentReference> docRefs = productid_list.stream()
                .map(pid -> firestore.collection(COLLECTION).document(pid))
                .toList();

        List<DocumentSnapshot> productDocs = firestore.getAll(docRefs.toArray(new DocumentReference[0])).get();

        List<ProductDetailedDTO> productDetailedList = new ArrayList<>();
        for (int i = 0; i < productDocs.size(); i++) {
            Product product = productDocs.get(i).toObject(Product.class);
            assert product != null;
            productDetailedList.add(new ProductDetailedDTO(
                    swpid_list.get(i),
                    productid_list.get(i),
                    product.getName(),
                    product.getPackage_size(),
                    product.getImage_url(),
                    wholesaler_products.get(i).getPrice(),
                    wholesaler_products.get(i).getStock()

            ));
        }
        return productDetailedList;
    }

    @Override
    public void addByPID(String PID, ProductDTO product) {
        firestore.collection(COLLECTION).document(PID).set(product);
    }

    @Override
    public void updateByPID(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(PID);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }

    @Override
    public Product findByProductName(String name) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("name", name).get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        DocumentSnapshot document = null;
        Product product = null;
        // Check if any documents match
        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            document = documents.getFirst();
            product = document.toObject(Product.class);
            product.setPid(document.getId());
        }

        return product;
    }

    @Override
    public DocumentSnapshot getUrlByName(String productName) throws ExecutionException, InterruptedException {
        CollectionReference products = firestore.collection("products");
        ApiFuture<QuerySnapshot> query = products.whereEqualTo("name", productName).get();

        QuerySnapshot querySnapshot = query.get();
        return querySnapshot.isEmpty() ? null : querySnapshot.getDocuments().getFirst();
    }

}
