package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a consumer's profile.
 * This class encapsulates the consumer's personal details and their address.
 * It utilizes Lombok annotations to automatically generate getters, setters,
 * toString, equals, hashCode, as well as constructors.
 * This DTO combines two other DTOs: {@link ConsumerDTO} for the consumer's
 * basic information (e.g., name, email, phone number) and {@link ConsumerAddressDTO}
 * for the consumer's address details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerProfileDTO {
    /**
     * The consumer's personal details, including first name, last name,
     * email, and phone number.
     */
    private ConsumerDTO consumer;
    /**
     * The consumer's address details, including street name, unit number,
     * building name, city, and postal code.
     */
    private ConsumerAddressDTO consumerAddress;
}
