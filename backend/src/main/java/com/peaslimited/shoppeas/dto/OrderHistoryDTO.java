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
    private String oid;
    private LocalDateTime date;
    private ArrayList<OrderWholesalerItemsDTO> orders;
    private String status;
    private double total_price;
}
