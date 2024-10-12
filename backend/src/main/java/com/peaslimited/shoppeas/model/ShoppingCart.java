package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    String cid;
    ArrayList<String> orders;
    String uid;
    double total_price;
}
