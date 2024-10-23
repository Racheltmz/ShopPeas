package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface OrderHistoryRepository {

    ArrayList<OrderHistoryDTO> getOrderHistoryByUID(String uid) throws ExecutionException, InterruptedException;

}
