package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderHistoryRepository {

    List<OrderHistoryDTO> getOrderHistoryByUID(String uid) throws ExecutionException, InterruptedException;

    void addOrderHistory(String uid, ArrayList<Object> cartList) throws ExecutionException, InterruptedException, IOException, URISyntaxException;
}
