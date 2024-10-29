package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;
import com.peaslimited.shoppeas.repository.OrderHistoryRepository;
import com.peaslimited.shoppeas.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public List<OrderHistoryDTO> getOrderHistory(String uid) throws ExecutionException, InterruptedException {
        return orderHistoryRepository.getOrderHistoryByUID(uid);
    }

    @Override
    public void addOrderHistory(String uid, ArrayList<Object> cartList) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        orderHistoryRepository.addOrderHistory(uid, cartList);
    }

}
