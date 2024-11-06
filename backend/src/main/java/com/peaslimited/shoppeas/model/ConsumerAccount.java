package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * This class represents a consumer's account.
 * It utilizes Lombok annotations to automatically generate getter, setter,
 * toString, equals, hashCode, as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAccount {
    /**
     * The unique identifier for the consumer account (user ID).
     */
    @Id
    private String UID;
    /**
     * A list of the user's payment method details.
     */
    private ArrayList<Object> paymentMtds;
}
