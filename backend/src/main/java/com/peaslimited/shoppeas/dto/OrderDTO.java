package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//NOT USED
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    //seller, product ID
    private String swp_id;

    //quantity of product
    private int quanity;

    //price of order
    private double price;

    //type: TRANSACTION, CART
    private String type;
}
