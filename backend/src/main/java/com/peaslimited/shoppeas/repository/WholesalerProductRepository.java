package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WholesalerProductRepository {

    // method to find the wholesaler by uen
    List<ProductDTO> findByUEN(String uen) throws ExecutionException, InterruptedException;

    // method to find a product by product id
    List<WholesalerProductDetailsDTO> findByPid(String pid) throws ExecutionException, InterruptedException;

    WholesalerProductDTO findBySwp_id(String swp_id) throws ExecutionException, InterruptedException;

    void addWholesalerProduct(WholesalerProductDTO product);

    void updateWholesalerProduct(String id, Map<String, Object> updates) throws ExecutionException, InterruptedException;

    void deleteWholesalerProduct(String id);

    String getWholesalerProductName(String swpid) throws ExecutionException, InterruptedException;
}