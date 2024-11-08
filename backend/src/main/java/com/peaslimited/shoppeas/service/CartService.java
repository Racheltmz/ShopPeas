package com.peaslimited.shoppeas.service;

import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * This interface handles operations related to shopping cart functionality, such as
 * retrieving, adding, updating, and deleting cart items.
 */
public interface CartService {

    /**
     * Retrieves the shopping cart for a user by their user ID (UID).
     * @param uid The unique identifier of the user whose cart contents are to be retrieved.
     * @return A map containing the cart details, including a list of transactions and their associated products.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    Map<String, Object> getCartByUID(String uid) throws ExecutionException, InterruptedException;

    /**
     * Adds an item to the user's cart.
     *
     * @param uid The user ID of the user adding the item to their cart.
     * @param data A map containing the data of the product to be added (e.g., swp_id, quantity, total price).
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws ResponseStatusException If there are validation errors, such as invalid product or quantity.
     */
    void addCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException, ResponseStatusException;

    /**
     * Updates the quantity of a product in the user's cart.
     *
     * @param uid The user ID of the user updating their cart.
     * @param data A map containing the data for the update (e.g., swp_id, quantity, price).
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws ResponseStatusException If the wholesaler or product does not exist, or if the cart is not found.
     */
    void updateCartQuantity(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Deletes a product from the user's cart.
     *
     * @param uid The user ID of the user deleting the product.
     * @param data A map containing the data of the product to be deleted (e.g., swp_id, uen).
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    void deleteCartProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Checks if the quantity in addToCart() is valid (i.e., quantity >=0)
     * @param quantity The quantity of a product being added to cart.
     * @return True if quantity is valid, false otherwise.
     */
    boolean checkQuantity(int quantity);

}
