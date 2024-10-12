package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;

import java.util.ArrayList;

public class ShoppingCartMapper
{
    public static ShoppingCartDTO toCartDTO(ArrayList<String> orders, String uid, double total_price)
    {
        return new ShoppingCartDTO(orders, uid, total_price);
    }

}
