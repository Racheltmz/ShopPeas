package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a wholesaler or consumer's current location.
 * This class encapsulates the consumer's personal details and their address.
 * It utilizes Lombok annotations to automatically generate getters, setters,
 * toString, equals, hashCode, as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {
    /**
     * The time taken to travel between the consumer's and wholesaler's respective locations.
     */
    private int duration;
    /**
     * The distance between the consumer's and wholesaler's respective locations.
     */
    private double distance;
}
