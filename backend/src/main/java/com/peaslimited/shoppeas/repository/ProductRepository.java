package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.model.WholesalerProducts;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductRepository {

    ProductDTO findByPID(String pid) throws ExecutionException, InterruptedException;

    List<Product> findAll() throws ExecutionException, InterruptedException;

    List<ProductDetailedDTO> findProductDetails(List<String> swpid_list, List<String> productid_list, List<WholesalerProducts> wholesaler_products) throws ExecutionException, InterruptedException;

    void addByPID(String pid, ProductDTO product);

    void updateByPID(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    Product findByProductName(String name) throws ExecutionException, InterruptedException;

}
