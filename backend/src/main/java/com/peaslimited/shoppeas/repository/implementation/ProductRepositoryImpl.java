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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


/**
 * Implementation of ProductRepository for managing product data in Firestore,
 * including methods to retrieve, add, update, and find product details.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final String COLLECTION = "products";

    @Autowired
    private Firestore firestore;

    /**
     * {@inheritDoc}
     *
     * Retrieves a product document from Firestore based on the provided PID.
     * Converts the document into a {@link ProductDTO} if it exists.
     *
     * @param pid the unique product ID
     * @return a {@link ProductDTO} containing product details, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Fetches all product documents from Firestore and maps each document to a {@link Product} object.
     *
     * @return a list of {@link Product} objects representing all products
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Retrieves detailed product information by mapping Firestore documents to {@link ProductDetailedDTO}.
     *
     * @param swpid_list a list of wholesaler product IDs
     * @param productid_list a list of product IDs
     * @param wholesaler_products a list of {@link WholesalerProducts} objects
     * @return a list of {@link ProductDetailedDTO} containing detailed product information
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * {@inheritDoc}
     *
     * Adds a new product document to Firestore with the specified PID as the document ID.
     *
     * @param PID the unique product ID
     * @param product a {@link ProductDTO} containing details of the product to add
     */
    @Override
    public void addByPID(String PID, ProductDTO product) {
        firestore.collection(COLLECTION).document(PID).set(product);
    }

    /**
     * {@inheritDoc}
     *
     * Updates an existing product document in Firestore with new data based on the provided PID.
     * Each key-value pair in the {@code data} map represents a field to be updated.
     *
     * @param PID the unique product ID
     * @param data a {@link Map} containing the fields to update and their new values
     * @throws ExecutionException 
     * @throws InterruptedException
     */
    @Override
    public void updateByPID(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(PID);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }

    /**
     * {@inheritDoc}
     *
     * Searches for a product document in Firestore based on its name.
     * Converts the first matching document into a {@link Product} object if it exists.
     *
     * @param name the name of the product
     * @return a {@link Product} object representing the product with the specified name, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Retrieves a Firestore document snapshot containing the URL of a product's image based on its name.
     *
     * @param productName the name of the product
     * @return a {@link DocumentSnapshot} containing the product's image URL, or null if no match is found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public DocumentSnapshot getUrlByName(String productName) throws ExecutionException, InterruptedException {
        CollectionReference products = firestore.collection("products");
        ApiFuture<QuerySnapshot> query = products.whereEqualTo("name", productName).get();

        QuerySnapshot querySnapshot = query.get();
        return querySnapshot.isEmpty() ? null : querySnapshot.getDocuments().getFirst();
    }

}
