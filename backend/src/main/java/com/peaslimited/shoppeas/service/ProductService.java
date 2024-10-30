package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.model.Product;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductService {

    // method to get a product by its ID
    ProductDTO getProductById(String id) throws ExecutionException, InterruptedException;

    // method to get all products
    List<Product> getAllProducts() throws ExecutionException, InterruptedException;

    void addProduct(String PID, ProductDTO product);

    void updateProduct(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    Product findByProductName(String name) throws ExecutionException, InterruptedException;

    String getImageURLByProductName(String productName) throws ExecutionException, InterruptedException;
}
