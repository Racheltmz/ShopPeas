package com.peaslimited.shoppeas.dto;

import com.google.cloud.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


/**
 * Data Transfer Object (DTO) for representing a consumer's order history, which is created after checkout.
 * This class encapsulates information about the user's orders, including
 * the date of the order, a list of the orders, the total price, and the user's
 * ID (UID). It utilizes Lombok annotations to automatically
 * generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryByUserDTO {
    /**
     * The timestamp of when the order was placed.
     */
    private Timestamp date;
    /**
     * A list of transaction IDs associated with the consumer for the specific order placed.
     */
    private ArrayList<String> orders;
    /**
     * The total price of all orders placed.
     */
    private double total_price;
    /**
     * The consumer's ID of the consumer who placed the order.
     */
    private String uid;
}
