package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        DocumentReference docRef = firestore.collection(COLLECTION).document(PID);

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
    public List<ProductDetailedDTO> findProductDetails(List<String> productid_list, List<WholesalerProducts> wholesaler_products) throws ExecutionException, InterruptedException {
        List<DocumentReference> docRefs = productid_list.stream()
                .map(pid -> firestore.collection(COLLECTION).document(pid))
                .toList();

        List<DocumentSnapshot> productDocs = firestore.getAll(docRefs.toArray(new DocumentReference[0])).get();

        List<ProductDetailedDTO> productDetailedList = new ArrayList<>();
        for (int i = 0; i < productDocs.size(); i++) {
            Product product = productDocs.get(i).toObject(Product.class);
            assert product != null;
            productDetailedList.add(new ProductDetailedDTO(
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

}
