package com.peaslimited.shoppeas.dto;

import java.util.ArrayList;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerTransactionsDTO {
    
    //user ID
    private String uid;

    //List of orders of type Order
    private ArrayList<String> orders;

    //total price of 1 transaction
    private double total_price;

    //date transaction placed (i.e., payment made)
    private String date;

    //status: PENDING ACCEPTANCE, PENDING COMPLETION, COMPLETED
    private String status;
}
