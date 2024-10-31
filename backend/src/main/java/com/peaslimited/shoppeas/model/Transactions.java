package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {
    @Id
    private String tid;
    private Map<String, Object> products;
    private boolean rated;
    // IN-CART, PENDING-ACCEPTANCE, PENDING-COMPLETION, COMPLETED
    private String status;
    private double total_price;
    private String uen;
    private String uid;
}
