package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerDTO {
    private String UEN;
    private String name;
    private String email;
    private String phone_number;
    private String currency;
    private Double rating;
    private ArrayList<Integer> num_ratings;
}
