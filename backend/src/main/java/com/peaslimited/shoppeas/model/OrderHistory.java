package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {

    @Id
    private String oid;
    private ArrayList<String> orders; //list of tid
    private LocalDate date;
    private double total_price;
    private String uid;
}
