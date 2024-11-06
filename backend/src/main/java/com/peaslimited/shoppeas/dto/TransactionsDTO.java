package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data Transfer Object (DTO) representing a transaction. Each transaction contains wholesaler products from one wholesaler,
 * such as the products in the transaction, its current status, total price, user-related identifiers, and whether the
 * transaction has been rated upon completion.
 * It utilizes Lombok annotations to automatically generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDTO {
    /**
     * A map representing the products involved in the transaction.
     * The keys include "price", "quantity", "swp_id" (wholesaler product ID)
     * and the value contains the corresponding information related to the product.
     */
    private Map<String, Object> products;
    /**
     * A boolean flag indicating whether the transaction has been rated by the user.
     * If true, the user has rated the transaction; otherwise, it remains unrated.
     */
    private boolean rated;
    /**
     * The current status of the transaction, with values such as:
     * - "IN-CART": The transaction is still in the cart.
     * - "PENDING-ACCEPTANCE": The transaction is awaiting acceptance.
     * - "PENDING-COMPLETION": The transaction is in progress and awaiting completion.
     * - "COMPLETED": The transaction has been completed.
     */
    private String status;
    /**
     * The total price of the transaction.
     */
    private double total_price;
    /**
     * The Unique Entity Number (UEN) associated with the wholesaler that the products are from.
     */
    private String uen;
    /**
     * The consumer's ID.
     */
    private String uid;
}