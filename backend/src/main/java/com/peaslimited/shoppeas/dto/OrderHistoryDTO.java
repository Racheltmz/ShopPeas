package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Data Transfer Object (DTO) for formatting the user's order history data, which is retrieved when the user visits the order history page.
 * This class encapsulates information about the order id, date, and list of transactions. It utilizes Lombok annotations
 * to automatically generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDTO {
    /**
     * Order ID
     */
    private String oid;
    /**
     * Order Date
     */
    private LocalDate date;
    /**
     * List of orders
     */
    private ArrayList<OrderWholesalerItemsDTO> orders;
}
