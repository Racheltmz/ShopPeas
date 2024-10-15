package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.mapper.ProductMapper;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class WholesalerProductServiceImpl implements WholesalerProductService {

    @Autowired
    WholesalerProductRepository wholesalerProductRepository;

    @Override
    public List<WholesalerProductDTO> findByPid(String pid) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.findByPid(pid);
    }


    @Override
    public List<WholesalerProductDTO> getByWholesalerUEN(String uen) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.findByUEN(uen)
                .stream()
                .map(ProductMapper::wholesalerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addWholesalerProduct(WholesalerProductDTO wholesalerProductDTO) {
        wholesalerProductRepository.addWholesalerProduct(wholesalerProductDTO);
    }

    @Override
    public void updateWholesalerProduct(String swpid, Map<String, Object> updates) throws ExecutionException, InterruptedException {
        wholesalerProductRepository.updateWholesalerProduct(swpid, updates);
    }

    @Override
    public void deleteWholesalerProduct(String swpid) {
        wholesalerProductRepository.deleteWholesalerProduct(swpid);
    }



}
