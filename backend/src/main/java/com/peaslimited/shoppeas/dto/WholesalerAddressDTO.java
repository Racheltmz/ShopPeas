package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

/**
 * Data Transfer Object (DTO) for representing a wholesaler's address and utilizes Lombok annotations
 * to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerAddressDTO {
    /**
     * The street name of the wholesaler's address.
     */
    private String street_name;
    /**
     * The unit number or apartment number in the wholesaler's address.
     * Can be null if not applicable.
     */
    @Nullable
    private String unit_no;
    /**
     * The name of the building, if applicable, for the wholesaler's address.
     * Can be null if not applicable.
     */
    @Nullable
    private String building_name;
    /**
     * The city of the wholesaler's address.
     */
    private String city;
    /**
     * The postal code of the wholesaler's address.
     */
    private String postal_code;
}