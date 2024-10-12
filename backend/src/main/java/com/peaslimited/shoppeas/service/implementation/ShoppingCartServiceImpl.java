package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.repository.ShoppingCartRepository;
import com.peaslimited.shoppeas.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository cartRepo;

    @Override
    public void createCart(String cid, ShoppingCartDTO cartDTO)
    {
        cartRepo.addByCID(cid, cartDTO);
    }

    @Override
    public ShoppingCartDTO getCartByCID(String cid) throws ExecutionException, InterruptedException
    {
        return cartRepo.findByCID(cid);
    }

    @Override
    public ShoppingCartDTO getCartByUID(String uid) throws ExecutionException, InterruptedException
    {
        return cartRepo.findByUID(uid);
    }

    @Override
    public void updateCartByOrder(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException
    {
        cartRepo.updateCartWithOrder(cid, data);
    }

}
