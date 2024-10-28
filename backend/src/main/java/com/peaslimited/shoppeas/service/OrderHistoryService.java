package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderHistoryService {

    List<OrderHistoryDTO> getOrderHistory(String uid) throws ExecutionException, InterruptedException;

    void addOrderHistory(String uid, ArrayList<String> uenList, ArrayList<String> transactionList, ArrayList<Double> checkoutPrice) throws ExecutionException, InterruptedException, IOException, URISyntaxException;
}
