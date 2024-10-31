package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsOrderedDTO {
    private ArrayList<Object> products;
    private String status;
    private double total_price;
    private String uen;
    private String uid;
    private boolean rated;
}
