package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.model.Product;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This interface provides various methods for managing products being sold on ShopPeas such as retrieving product
 * details, adding new products, updating existing products, and fetching product images.
 */
public interface ProductService {

    /**
     * Retrieves product details for a given product ID (PID).
     *
     * @param id The product ID of the product whose details are to be retrieved.
     * @return A {@link ProductDTO} object containing the product details.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve product details.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    // method to get a product by its ID
    ProductDTO getProductById(String id) throws ExecutionException, InterruptedException;

    /**
     * Retrieves a list of all products in the system.
     *
     * @return A list of {@link Product} objects representing all the products in the system.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve all products.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    // method to get all products
    List<Product> getAllProducts() throws ExecutionException, InterruptedException;

    /**
     * Adds a new product to the system using the given product details.
     *
     * @param PID The product ID for the new product to be added.
     * @param product A {@link ProductDTO} object containing the product details.
     */
    void addProduct(String PID, ProductDTO product);

    /**
     * Updates an existing product's details using the given product ID and the data to be updated.
     *
     * @param PID The product ID of the product to be updated.
     * @param data A map containing the product data to be updated (field names as keys and values to update).
     * @throws ExecutionException If an error occurs during the execution of a task to update the product.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    void updateProduct(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Retrieves a product based on its name.
     *
     * @param name The name of the product to be retrieved.
     * @return A {@link Product} object representing the product with the given name.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve the product.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    Product findByProductName(String name) throws ExecutionException, InterruptedException;

    /**
     * Retrieves the image URL for a product based on its name.
     *
     * @param productName The name of the product whose image URL is to be retrieved.
     * @return The image URL of the product if available, otherwise {@code null}.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve the image URL.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    String getImageURLByProductName(String productName) throws ExecutionException, InterruptedException;
}
