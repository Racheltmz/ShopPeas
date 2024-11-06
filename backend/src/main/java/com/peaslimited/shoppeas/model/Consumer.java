package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * This class represents a consumer's basic information such as name, phone number, and email.
 * It utilizes Lombok annotations to automatically generate getters, setters,
 * toString, equals, hashCode, as well as constructors for ease of use.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer {
    /**
     * The unique identifier for the consumer (user ID) and is a database-generated ID by firebase.
     */
    @Id
    private String UID;
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
