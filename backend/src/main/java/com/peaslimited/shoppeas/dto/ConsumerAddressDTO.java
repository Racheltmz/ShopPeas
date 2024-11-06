package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

/**
 * Data Transfer Object (DTO) for representing a consumer's address.
 * This class holds the consumer's address details and utilizes Lombok annotations
 * to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
public class ConsumerAddressDTO {
    /**
     * The street name of the consumer's address.
     */
    private String street_name;
    /**
     * The unit number or apartment number in the consumer's address.
     * Can be null if not applicable.
     */
    private String unit_no;
    /**
     * The name of the building, if applicable, for the consumer's address.
     * Can be null if not applicable.
     */
    private String building_name;
    /**
     * The city of the consumer's address.
     */
    private String city;
    /**
     * The postal code of the consumer's address.
     */
    private String postal_code;
}
