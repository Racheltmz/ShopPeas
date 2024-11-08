package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.repository.ProductRepository;
import com.peaslimited.shoppeas.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * This class is an implementation of the {@link ProductService} interface and provides various methods for managing
 * products being sold on ShopPeas such as retrieving product details, adding new products,
 * updating existing products, and fetching product images.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * Repository for interacting with product data.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves product details for a given product ID (PID).
     *
     * @param pid The product ID of the product whose details are to be retrieved.
     * @return A {@link ProductDTO} object containing the product details.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve product details.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    @Override
    // get Product details
    public ProductDTO getProductById(String pid) throws ExecutionException, InterruptedException {
        return productRepository.findByPID(pid);
    }

    /**
     * Retrieves a list of all products in the system.
     *
     * @return A list of {@link Product} objects representing all the products in the system.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve all products.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    @Override
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException{
        return productRepository.findAll();
    }

    /**
     * Adds a new product to the system using the given product details.
     *
     * @param PID The product ID for the new product to be added.
     * @param product A {@link ProductDTO} object containing the product details.
     */
    @Override
    public void addProduct(String PID, ProductDTO product){
        productRepository.addByPID(PID, product);
    }

    /**
     * Updates an existing product's details using the given product ID and the data to be updated.
     *
     * @param PID The product ID of the product to be updated.
     * @param data A map containing the product data to be updated (field names as keys and values to update).
     * @throws ExecutionException If an error occurs during the execution of a task to update the product.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    @Override
    public void updateProduct(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        productRepository.updateByPID(PID,data);
    }

    /**
     * Retrieves a product based on its name.
     *
     * @param name The name of the product to be retrieved.
     * @return A {@link Product} object representing the product with the given name.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve the product.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    @Override
    public Product findByProductName(String name) throws ExecutionException, InterruptedException
    {
        return productRepository.findByProductName(name);
    }

    /**
     * Retrieves the image URL for a product based on its name.
     *
     * @param productName The name of the product whose image URL is to be retrieved.
     * @return The image URL of the product if available, otherwise {@code null}.
     * @throws ExecutionException If an error occurs during the execution of a task to retrieve the image URL.
     * @throws InterruptedException If the thread executing the task is interrupted while waiting for the result.
     */
    @Override
    public String getImageURLByProductName(String productName) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = productRepository.getUrlByName(productName);
        return (document != null && document.contains("image_url")) ? document.getString("image_url") : null;
    }

}
