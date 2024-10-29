package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAccountDTO {
    //private ArrayList<Object> paymentMtds;
    private Map<String, Object> paymentMtds;
}