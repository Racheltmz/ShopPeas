package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {

    @Id
    private String oid;
    private ArrayList<String> orders; //list of tid
    private LocalDateTime date;
    private float total_price;
    private String uid;
}
