package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailedDTO {
    private String swp_id;
    private String pid;
    private String name;
    private String package_size;
    private String image_url;
    private double price;
    private int stock;
}
