package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.model.WholesalerAddress;
import com.peaslimited.shoppeas.model.WholesalerProducts;
import com.peaslimited.shoppeas.repository.ProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerAddressRepository;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class WholesalerProductRepositoryImpl implements WholesalerProductRepository {

    private final String COLLECTION = "wholesaler_products";

    @Autowired
    private Firestore firestore;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WholesalerRepository wholesalerRepository;

    @Autowired
    private WholesalerAddressRepository wholesalerAddressRepository;

    @Override
    // Fetch wholesaler products by uen
    public List<ProductDetailedDTO> findByUEN(String uen) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection(COLLECTION).whereEqualTo("uen", uen).whereEqualTo("active", true).get().get();

        List<WholesalerProducts> products = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .toList();

        List<String> productid_list = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .map(WholesalerProducts::getPid)
                .toList();

        return productRepository.findProductDetails(productid_list, products);
    }

    // Fetch products by their PID
    @Override
    public List<WholesalerProductDetailsDTO> findByPid(String pid) throws ExecutionException, InterruptedException {
        // Query database to get all wholesaler products with the given PID
        QuerySnapshot snapshot = firestore.collection(COLLECTION)
                .whereEqualTo("pid", pid)
                .whereEqualTo("active", true)
                .get().get();

        List<WholesalerProducts> wholesalerProducts = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .toList();

        List<String> uen_list = snapshot.getDocuments().stream()
                .map(doc -> doc.toObject(WholesalerProducts.class))
                .map(WholesalerProducts::getUen)
                .toList();

        // Get wholesaler info
        List<WholesalerDTO> wholesalers = wholesalerRepository.findWholesalers(uen_list);

        // Get wholesaler address info
        List<WholesalerAddress> wholesalerAddresses = wholesalerAddressRepository.findAllWholesalerAddress(wholesalerProducts);

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

    @Override
    public String getWholesalerProductName(String swp_id) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProduct = findBySwp_id(swp_id);
        String pid = wholesalerProduct.getPid();

        ProductDTO product = productService.getProductById(pid);

        return product.getName();
    }

    @Override
    public String getWholesalerProductDesc(String swp_id) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProduct = findBySwp_id(swp_id);
        String pid = wholesalerProduct.getPid();

        ProductDTO product = productService.getProductById(pid);

        return product.getPackage_size();
    }

    @Override
    public String getWholesalerProductImg(String swp_id) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProduct = findBySwp_id(swp_id);
        String pid = wholesalerProduct.getPid();

        ProductDTO product = productService.getProductById(pid);

        return product.getImage_url();
    }

    // Add a new wholesaler product
    @Override
    public void addWholesalerProduct(WholesalerProductDTO wholesalerProductDTO) {
        firestore.collection(COLLECTION).add(wholesalerProductDTO); // Firebase generates ID
    }

    @Override
    public void updateWholesalerProduct(String swp_id, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(swp_id);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }

    @Override
    public void deleteWholesalerProduct(String swp_id) {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(swp_id);
        docRef.update("active", false);
    }

    @Override
    public WholesalerProducts getWProductByPIDandUEN(String pid, String uen) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("pid", pid)
                .whereEqualTo("uen", uen).get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        DocumentSnapshot document = null;

        WholesalerProducts product = null;
        // Check if any documents match
        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            document = documents.getFirst();
            product = document.toObject(WholesalerProducts.class);
        }

        return product;
    }

}
