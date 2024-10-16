package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id

    //order ID
    private String oid;

    //seller, product ID
    private String swp_id;

    //quantity of product
    private int quanity;

    //price of order
    private double price;

    //type: TRANSACTION, CART
    private String type;
}
