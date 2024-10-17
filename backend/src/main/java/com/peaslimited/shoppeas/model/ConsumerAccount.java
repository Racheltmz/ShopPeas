package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAccount {
    @Id
    private String UID;
    private ArrayList<Object> paymentMtds;
}
