package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing detailed information about a product, such as its unique identifier, name, pricing,
 * amount per item (package_size), stock availability, and image URL. It utilizes Lombok annotations to automatically
 * generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailedDTO {
    /**
     * The wholesaler product ID.
     */
    private String swp_id;
    /**
     * The product ID.
     */
    private String pid;
    /**
     * The product name.
     */
    private String name;
    /**
     * The amount per item (e.g., 300g for a packet of tofu).
     */
    private String package_size;
    /**
     * The product's image URL.
     */
    private String image_url;
    /**
     * The product price.
     */
    private double price;
    /**
     * The product stock.
     */
    private int stock;
}
