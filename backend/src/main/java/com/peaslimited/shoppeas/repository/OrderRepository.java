package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.OrderDTO;

public interface OrderRepository {

    void addByOID(String OID, OrderDTO orderDTO);
}
