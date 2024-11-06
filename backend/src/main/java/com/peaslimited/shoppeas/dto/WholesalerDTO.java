package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
/**
 * Data Transfer Object (DTO) representing basic information about a wholesaler.
 * This class contains details such as the wholesaler's unique entity number (UEN),
 * name, contact information, currency, and ratings. The class utilizes Lombok annotations
 * to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerDTO {
    /**
     * The Unique Entity Number (UEN) of the wholesaler.
     */
    private String UEN;
    /**
     * The wholesaler's business name.
     */
    private String name;
    /**
     * The wholesaler's email address.
     */
    private String email;
    /**
     * The wholesaler's phone number.
     */
    private String phone_number;
    /**
     * The wholesaler's preferred currency.
     */
    private String currency;
    /**
     * The wholesaler's average rating.
     */
    private Double rating;
    /**
     * List of the number of ratings per rating value (e.g., number of ratings for 1 star, 2 stars..., 5 stars).
     */
    private ArrayList<Integer> num_ratings;
}
