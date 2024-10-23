package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderWholesalerItemsDTO {
    private String wholesalerName;
    private ArrayList<OrderItemDTO> items;
}
