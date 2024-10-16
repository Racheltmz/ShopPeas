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

    // other crud methods
    void addWholesalerProduct(WholesalerProductDTO product);

    void deleteWholesalerProduct(String swpid);

    void updateWholesalerProduct(String swpid, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
