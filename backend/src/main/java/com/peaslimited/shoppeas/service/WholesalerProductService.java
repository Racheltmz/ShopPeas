package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.WholesalerProductDTO;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface WholesalerProductService {
    // to find a particular product but different wholesalers
    List<WholesalerProductDTO> findByPid(String pid) throws ExecutionException, InterruptedException;

    // method to find all the products by a particular wholesaler
    List<WholesalerProductDTO> getByWholesalerUEN(String uen) throws ExecutionException, InterruptedException;

    WholesalerProductDTO getBySwp_id(String swp_id) throws ExecutionException, InterruptedException;

    // other crud methods
    void addWholesalerProduct(WholesalerProductDTO product);

    void updateWholesalerProduct(String swpid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void deleteWholesalerProduct(String swpid);

    String getWholesalerProductName(String swpid) throws ExecutionException, InterruptedException;
}
