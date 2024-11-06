package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Data Transfer Object (DTO) representing a wholesaler's rating, such as the average rating
 * and the number of ratings per rating value (e.g., number of ratings for 1 star, 2 stars..., 5 stars).
 * It utilizes Lombok annotations to automatically generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    /**
     * A wholesaler's average rating.
     */
    private Double rating;
    /**
     * List of the number of ratings per rating value (e.g., number of ratings for 1 star, 2 stars..., 5 stars).
     */
    private ArrayList<Integer> num_ratings;
}
