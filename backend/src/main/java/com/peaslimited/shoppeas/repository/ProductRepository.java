package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.model.Product;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductRepository {

    ProductDTO findByPID(String PID) throws ExecutionException, InterruptedException;

    List<Product> findAll() throws ExecutionException, InterruptedException;

    List<Product> findProductDetails(List<String> products) throws ExecutionException, InterruptedException;

    void addByPID(String PID, ProductDTO product);

    void updateByPID(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    Product findByProductName(String name) throws ExecutionException, InterruptedException;
}
