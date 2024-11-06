package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for representing a consumer's basic information
 * such as name, phone number, and email. It utilizes Lombok annotations to automatically generate getters, setters,
 * toString, equals, hashCode, as well as constructors for ease of use.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerDTO {
    /**
     * The consumer's first name.
     */
    private String first_name;
    /**
     * The consumer's last name.
     */
    private String last_name;
    /**
     * The consumer's email address.
     */
    private String email;
    /**
     * The consumer's phone number.
     */
    private String phone_number;
}
