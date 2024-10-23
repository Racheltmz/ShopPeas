package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;
import com.peaslimited.shoppeas.model.OrderHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderHistoryService {

    List<OrderHistoryDTO> getOrderHistory(String uid) throws ExecutionException, InterruptedException;
}
