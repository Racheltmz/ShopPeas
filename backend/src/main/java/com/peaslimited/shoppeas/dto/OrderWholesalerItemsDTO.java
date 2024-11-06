package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Data Transfer Object (DTO) for representing the user's order history details, which is retrieved when the user visits the order history page.
 * This class encapsulates information about the transaction id, wholesaler UEN, wholesaler name, purchased product items,
 * transaction status, total price of transaction, and a boolean variable to track if the transaction has been rated.
 * It utilizes Lombok annotations to automatically generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderWholesalerItemsDTO {
    /**
     * Transaction ID
     */
    private String tid;
    /**
     * Wholesaler UEN
     */
    private String uen;
    /**
     * Wholesaler Name
     */
    private String wholesalerName;
    /**
     * Product items purchased by the user from the wholesaler
     */
    private ArrayList<OrderItemDTO> items;
    /**
     * Transaction Status
     */
    private String status;
    /**
     * Total Price of Transaction
     */
    private double total_price;
    /**
     * Whether the transaction has been rated
     */
    private boolean rated;
}
