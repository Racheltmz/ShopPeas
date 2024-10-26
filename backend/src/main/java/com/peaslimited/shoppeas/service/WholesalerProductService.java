package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WholesalerProductService {
    // to find a particular product but different wholesalers
    List<WholesalerProductDetailsDTO> findByPid(String pid) throws ExecutionException, InterruptedException;

    List<ProductDetailedDTO> getByWholesalerUID(String uid) throws ExecutionException, InterruptedException;

    // method to find all the products by a particular wholesaler
    List<ProductDetailedDTO> getByWholesalerUEN(String uen) throws ExecutionException, InterruptedException;

    WholesalerProductDTO getBySwp_id(String swp_id) throws ExecutionException, InterruptedException;

    // other crud methods
    void addWholesalerProduct(WholesalerProductDTO product);

    void updateWholesalerProduct(String swp_id, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void deleteWholesalerProduct(String swp_id);

    String getWholesalerProductName(String swp_id) throws ExecutionException, InterruptedException;
}
