package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class WholesalerProductRepositoryImpl implements WholesalerProductRepository {

    private final String COLLECTION = "wholesaler_products";

    @Autowired
    private Firestore firestore;

    @Autowired
    private ProductService productService;

    @Override
    // Fetch wholesaler products by uen
    public List<Product> findByUEN(String uen) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection(COLLECTION).whereEqualTo("uen", uen).whereEqualTo("active", true).get().get();

        List<String> products = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .map(WholesalerProducts::getPid)
                .toList();

        List<DocumentReference> docRefs = products.stream()
                .map(pid -> firestore.collection("products").document(pid))
                .toList();

        List<DocumentSnapshot> productDocs = firestore.getAll(docRefs.toArray(new DocumentReference[0])).get();

        return productDocs.stream()
                .filter(DocumentSnapshot::exists)
                .map(doc -> {
                    Product product = doc.toObject(Product.class);
                    assert product != null;
                    product.setPid(doc.getId());  // Assuming Product class has setId method
                    return product;
                })
                .collect(Collectors.toList());
    }

    // Fetch products by their PID
    @Override
    public List<WholesalerProductDetailsDTO> findByPid(String pid) throws ExecutionException, InterruptedException {
        // Query Firestore to get all wholesaler products with the given PID
        QuerySnapshot snapshot = firestore.collection(COLLECTION)
                .whereEqualTo("pid", pid)
                .whereEqualTo("active", true)
                .get().get();

        List<WholesalerProducts> wholesalerProducts = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .toList();

        List<String> uens = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .map(WholesalerProducts::getUen)
                .toList();

        // Get wholesaler info
        CollectionReference wholesalerCollection = firestore.collection("wholesaler");
        Query wholesalerQuery = wholesalerCollection.whereIn("uen", uens);
        QuerySnapshot productSnapshot = wholesalerQuery.get().get();
        List<WholesalerDTO> wholesalers = productSnapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerDTO.class))  // Replace Product.class with your actual Product class
                .toList();

        // Get wholesaler address info
        List<DocumentReference> wholesalerAddressRefs = wholesalerProducts.stream()
                .map(doc -> firestore.collection("wholesaler_address").document(doc.getUen()))
                .toList();
        List<DocumentSnapshot> wholesalerAddressDocs = firestore.getAll(wholesalerAddressRefs.toArray(new DocumentReference[0])).get();

        List<WholesalerAddress> wholesalerAddresses = wholesalerAddressDocs.stream()
                .filter(DocumentSnapshot::exists)
                .map(doc -> doc.toObject(WholesalerAddress.class))
                .toList();

        // Combine product and wholesaler data into DTOs
        List<WholesalerProductDetailsDTO> wholesalerList = new ArrayList<>();

        for (int i = 0; i < wholesalerProducts.size(); i++) {
            wholesalerList.add(new WholesalerProductDetailsDTO(
                    wholesalers.get(i).getName(),
                    wholesalers.get(i).getUEN(),
                    wholesalerAddresses.get(i).getStreet_name(),
                    wholesalerProducts.get(i).getStock(),
                    wholesalerProducts.get(i).getPrice(),
                    wholesalers.get(i).getRating()
            ));
        }

        return wholesalerList;
    }

    @Override
    public WholesalerProductDTO findBySwp_id(String swp_id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(swp_id);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Consumer object
        WholesalerProductDTO wholesalerProductDTO = null;
        if (document.exists()) {
            wholesalerProductDTO = document.toObject(WholesalerProductDTO.class);
        }
        return wholesalerProductDTO;
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

    @Override
    public String getWholesalerProductName(String swpid) throws ExecutionException, InterruptedException {
        System.out.println(swpid);
        System.out.println("--");
        WholesalerProductDTO wholesalerProduct = findBySwp_id(swpid);
        String pid = wholesalerProduct.getPid();

        ProductDTO product = productService.getProductById(pid);

        return product.getName();
    }

}
