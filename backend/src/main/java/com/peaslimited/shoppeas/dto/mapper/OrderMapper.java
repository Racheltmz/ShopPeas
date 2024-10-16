package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.OrderDTO;

import java.util.ArrayList;

public class OrderMapper {

    public static OrderDTO toOrderDTO(double tot_price, int quantity, String swp_id, String type)
    {
        return new OrderDTO(swp_id, quantity, tot_price, type);
    }
}
