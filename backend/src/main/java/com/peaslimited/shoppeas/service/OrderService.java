package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.OrderDTO;

public interface OrderService {

    void addOrder(String oid, OrderDTO orderDTO);
}
