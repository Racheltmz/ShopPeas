package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.OrderDTO;
import com.peaslimited.shoppeas.repository.OrderRepository;
import com.peaslimited.shoppeas.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public void addOrder(String oid, OrderDTO orderDTO)
    {
        orderRepo.addByOID(oid, orderDTO);
    }
}
