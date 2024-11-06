package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a consumer's product item in the order history,
 * which is retrieved when the user visits the order history page.
 * This class encapsulates information about the product's name, description, image URL, the quantity selected by the user,
 * and the price the user paid for the product. It utilizes Lombok annotations to automatically
 * generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    /**
     * Product name
     */
    private String name;
    /**
     * Product description
     */
    private String description;
    /**
     * Product Image URL path
     */
    private String image_url;
    /**
     * Selected quantity
     */
    private int quantity;
    /**
     * Price paid for the product
     */
    private double price;
}
