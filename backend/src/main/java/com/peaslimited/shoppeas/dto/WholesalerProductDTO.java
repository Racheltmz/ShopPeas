package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class WholesalerProductDTO {
    private String uen;
    private String pid;
    private float price;
    private float stock;
}
