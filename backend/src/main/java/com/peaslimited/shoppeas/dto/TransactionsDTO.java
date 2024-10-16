package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDTO {
    private ArrayList<Object> products;
    // IN-CART, PENDING-ACCEPTANCE, PENDING-COMPLETION, COMPLETED
    private String status;
    private double total_price;
    private String uen;
    private String uid;
}
