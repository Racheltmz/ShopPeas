package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.ArrayList;
import java.time.LocalDateTime;

//saff

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerTransactions {
    @Id

    //transaction ID
    private String tid;

    //user ID
    private String uid;

    //List of orders of type Order
    private ArrayList<String> orders;

    //total price of 1 transaction
    private double total_price;

    //date transaction placed (i.e., payment made)
    private String date;

    /* STATUSES:
     * IN CART: added to cart, payment not made
     * PENDING ACCEPTANCE: payment made, transaction is now "to be accepted" by wholesaler
     * PENDING COMPLETION: transaction has been accepted by wholesaler, to be completed
     * COMPLETED: transaction has been completed
     */
    private String status;
}
