package com.peaslimited.shoppeas.dto;

import com.google.cloud.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryByUserDTO {
    private Timestamp date;
    private ArrayList<String> orders;
    private double total_price;
    private String uid;
}
