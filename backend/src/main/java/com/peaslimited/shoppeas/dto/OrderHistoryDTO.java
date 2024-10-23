package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDTO {
    private String oid;
    private LocalDate date;
    private ArrayList<OrderWholesalerItemsDTO> orders;
}
