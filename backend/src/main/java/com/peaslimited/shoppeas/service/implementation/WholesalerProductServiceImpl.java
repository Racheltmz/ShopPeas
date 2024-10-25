package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerProductServiceImpl implements WholesalerProductService {

    @Autowired
    WholesalerRepository wholesalerRepository;

    @Autowired
    WholesalerProductRepository wholesalerProductRepository;

    @Override
    public List<WholesalerProductDetailsDTO> findByPid(String pid) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.findByPid(pid);
    }

    @Override
    public List<ProductDetailedDTO> getByWholesalerUID(String uid) throws ExecutionException, InterruptedException {
        String uen = wholesalerRepository.findByUID(uid).getUEN();
        return wholesalerProductRepository.findByUEN(uen);
    }

    @Override
    public List<ProductDetailedDTO> getByWholesalerUEN(String uen) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.findByUEN(uen);
    }

    @Override
    public void addWholesalerProduct(WholesalerProductDTO wholesalerProductDTO) {
        wholesalerProductRepository.addWholesalerProduct(wholesalerProductDTO);
    }

    @Override
    public void updateWholesalerProduct(String swp_id, Map<String, Object> updates) throws ExecutionException, InterruptedException {
        wholesalerProductRepository.updateWholesalerProduct(swp_id, updates);
    }

    @Override
    public WholesalerProductDTO getBySwp_id(String swp_id) throws ExecutionException, InterruptedException
    {
        return wholesalerProductRepository.findBySwp_id(swp_id);
    }

    @Override
    public void deleteWholesalerProduct(String swp_id) {
        wholesalerProductRepository.deleteWholesalerProduct(swp_id);
    }

    @Override
    public String getWholesalerProductName(String swp_id) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.getWholesalerProductName(swp_id);
    }


}
