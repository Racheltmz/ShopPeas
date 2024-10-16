package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDTO {
    private ArrayList<String> orders; //list of tid
    private LocalDateTime date;
    private double total_price;
    private String uid;
}
