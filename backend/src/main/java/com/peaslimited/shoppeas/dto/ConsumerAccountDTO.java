package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
/**
 * Data Transfer Object (DTO) for representing a consumer's account.
 * This class holds information related to a consumer's payment methods.
 * It utilizes Lombok annotations to automatically generate getter, setter,
 * toString, equals, hashCode, as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerAccountDTO {
    /**
     * A map of payment methods associated with the consumer's account.
     * The key is a string identifier for the payment fields (such as card number, expiry date etc.),
     * and the value is an Object that can represent various payment method details
     */
    private Map<String, Object> paymentMtds;
}