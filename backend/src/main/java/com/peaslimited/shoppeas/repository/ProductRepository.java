package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.model.WholesalerProducts;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ProductRepository is an interface for performing operations related to products, 
 * including methods for finding, adding, updating, and retrieving product details.
 */
public interface ProductRepository {

    /**
     * Retrieves a product by its unique product ID (PID).
     *
     * @param pid the unique product ID
     * @return a {@link ProductDTO} containing product details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    ProductDTO findByPID(String pid) throws ExecutionException, InterruptedException;

    /**
     * Retrieves all products available in the database.
     *
     * @return a list of {@link Product} objects representing all products
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    List<Product> findAll() throws ExecutionException, InterruptedException;

    /**
     * Retrieves detailed product information based on a list of wholesaler products IDs
     * product IDs and wholesaler products 
     *
     * @param swpid_list a list of wholesaler product IDs
     * @param productid_list a list of product IDs
     * @param wholesaler_products a list of {@link WholesalerProducts} objects
     * @return a list of {@link ProductDetailedDTO} containing detailed product information
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    List<ProductDetailedDTO> findProductDetails(List<String> swpid_list, List<String> productid_list, List<WholesalerProducts> wholesaler_products) throws ExecutionException, InterruptedException;

    /**
     * Adds a new product to the database with the specified product ID (PID).
     *
     * @param pid the unique product ID
     * @param product a {@link ProductDTO} containing details of the product to add
     */
    void addByPID(String pid, ProductDTO product);

    /**
     * Updates an existing product based on its product ID (PID) with the provided data.
     *
     * @param PID the unique product ID
     * @param data a {@link Map} containing the fields to update and their new values
     * @throws ExecutionException
     * @throws InterruptedException
     */
    void updateByPID(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Finds a product by its name.
     *
     * @param name the name of the product
     * @return a {@link Product} object representing the product with the specified name
     * @throws ExecutionException
     * @throws InterruptedException 
     */
    Product findByProductName(String name) throws ExecutionException, InterruptedException;

    /**
     * Retrieves the URL of a product's image based on its name.
     *
     * @param ProductName the name of the product
     * @return a {@link DocumentSnapshot} containing the product's image URL
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    DocumentSnapshot getUrlByName(String ProductName) throws ExecutionException, InterruptedException;

}
