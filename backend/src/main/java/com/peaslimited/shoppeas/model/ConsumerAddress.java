package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.annotation.Nullable;

/**
 * This class represents a consumer's address details.
 * It utilizes Lombok annotations to automatically generate getter, setter,
 * toString, equals, hashCode, as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAddress {
    /**
     * The unique identifier for the consumer address details (user ID).
     */
    @Id
    private String UID;
    /**
     * The consumer's street name.
     */
    private String street_name;
    /**
     * The consumer's unit number, which can be a null value.
     */
    @Nullable
    private String unit_no;
    /**
     * The consumer's building name, which can be a null value.
     */
    @Nullable
    private String building_name;
    /**
     * The consumer's city.
     */
    private String city;
    /**
     * The consumer's postal code.
     */
    private String postal_code;

}
