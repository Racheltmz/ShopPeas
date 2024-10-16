package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    private String tid;
    private ArrayList<Object> products;
    // IN-CART, PENDING-ACCEPTANCE, PENDING-COMPLETION, COMPLETED
    private String status;
    private float total_price;
    private String uen;
    private String uid;
}
