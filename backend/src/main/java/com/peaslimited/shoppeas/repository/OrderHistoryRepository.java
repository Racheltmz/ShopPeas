package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;
import com.peaslimited.shoppeas.model.OrderHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderHistoryRepository {

    List<OrderHistoryDTO> getOrderHistoryByUID(String uid) throws ExecutionException, InterruptedException;

}
