package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class WholesalerProductDetailsDTO {
    private String name;
    private String uen;
    private String location;
    private int stock;
    private double price;
    private double ratings;
}