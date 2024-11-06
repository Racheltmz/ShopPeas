package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing detailed information about a product, such as its name,
 * amount per item (package_size), and image URL. It utilizes Lombok annotations to automatically
 * generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
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
}
