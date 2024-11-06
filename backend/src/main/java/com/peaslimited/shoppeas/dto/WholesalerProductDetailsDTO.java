package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) representing all the details of a product offered by a wholesaler.
 * This class encapsulates product information such as wholesaler product ID, wholesaler details, location,
 * stock, price, and ratings.
 * The class utilizes Lombok annotations to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class WholesalerProductDetailsDTO {
    /**
     * The wholesaler product ID.
     */
    private String swp_id;
    /**
     * The product name.
     */
    private String name;
    /**
     * The wholesaler's Unique Entity Number (UEN).
     */
    private String uen;
    /**
     * The wholesaler's location.
     */
    private String location;
    /**
     * The wholesaler's postal code.
     */
    private String postal_code;
    /**
     * The duration taken to travel between the consumer and wholesaler.
     */
    private int duration;
    /**
     * The distance between the consumer and wholesaler.
     */
    private double distance;
    /**
     * The quantity of the stock left for the product.
     */
    private int stock;
    /**
     * The price of the product.
     */
    private double price;
    /**
     * The ratings for the product
     */
    private double ratings;
}
