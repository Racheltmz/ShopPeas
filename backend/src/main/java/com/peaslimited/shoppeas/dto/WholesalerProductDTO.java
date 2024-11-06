package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing some details of a product offered by a wholesaler such as the wholesaler's UEN,
 * product ID, stock, price, ratings, and whether the product is still being sold (active).
 * The class utilizes Lombok annotations to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class WholesalerProductDTO {
    /**
     * The Unique Entity Number (UEN) of the wholesaler selling the product.
     */
    private String uen;
    /**
     * The product ID.
     */
    private String pid;
    /**
     * The price of the product.
     */
    private double price;
    /**
     * The quantity of the stock left for the product.
     */
    private Integer stock;
    /**
     * Boolean flag representing whether the item is still being sold by the wholesaler.
     */
    private Boolean active;
}
