package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
/**
 * Data Transfer Object (DTO) representing the item's in a consumer's cart, such as the orders and total price, and the user's ID.
 * It utilizes Lombok annotations to automatically generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {
    /**
     * A list of the transaction IDs of the items in the cart.
     */
    ArrayList<String> orders;
    /**
     * The consumer's ID to whom the cart belongs to.
     */
    String uid;
    /**
     * The total price of all the items in the cart.
     */
    double total_price;
}



