package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.repository.WholesalerAddressRepository;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerAddressServiceImpl implements WholesalerAddressService {

    @Autowired
    private WholesalerAddressRepository wholesalerAddressRepository;

    @Override
    public WholesalerAddressDTO getWholesalerAddress(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerAddressRepository.findByUEN(UEN);
    }

    @Override
    public void addWholesalerAddress(String UEN, WholesalerAddressDTO wholesalerAddress) {
        wholesalerAddressRepository.addByUEN(UEN, wholesalerAddress);
    }

    @Override
    public void updateWholesalerAddress(String UEN, Map<String, Object> data) {
        wholesalerAddressRepository.updateByUEN(UEN, data);
    }

}
