package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface OrderHistoryService {

    ArrayList<OrderHistoryDTO> getOrderHistory(String uid) throws ExecutionException, InterruptedException;
}
