package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.repository.WholesalerAccountRepository;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerAccountServiceImpl implements WholesalerAccountService {

    @Autowired
    private WholesalerAccountRepository wholesalerAccountRepository;

    @Override
    public WholesalerAccountDTO getWholesalerAccount(String UEN) throws ExecutionException, InterruptedException {
        return wholesalerAccountRepository.findByUEN(UEN);
    }

    @Override
    public void addWholesalerAccount(String UEN, WholesalerAccountDTO wholesalerAccount) {
        wholesalerAccountRepository.addByUEN(UEN, wholesalerAccount);
    }

    @Override
    public void updateWholesalerAccount(String UEN, Map<String, Object> data) {
        wholesalerAccountRepository.updateByUEN(UEN, data);
    }
}