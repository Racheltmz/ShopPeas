package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.model.WholesalerProducts;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WholesalerProductRepository {

    // method to find the wholesaler by uen
    List<WholesalerProducts> findByUEN(String uen) throws ExecutionException, InterruptedException;

    // method to find a product by productid
    List<WholesalerProductDTO> findByPid(String pid) throws ExecutionException, InterruptedException;

    void addWholesalerProduct(WholesalerProductDTO product);

    void updateWholesalerProduct(String id, Map<String, Object> updates) throws ExecutionException, InterruptedException;

    void deleteWholesalerProduct(String id);

}
