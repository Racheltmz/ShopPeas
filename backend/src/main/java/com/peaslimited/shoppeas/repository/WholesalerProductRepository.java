package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;
import com.peaslimited.shoppeas.model.WholesalerProducts;

import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * WholesalerProductRepository is an interface for managing and performing operations on 
 * wholesaler products including finding, adding, updating, and deleting products related to wholesalers.
 */
public interface WholesalerProductRepository {

    /**
     * Finds all products associated with a specific wholesaler by their UEN.
     * 
     * @param uen the unique entity number of the wholesaler
     * @return a list of {@link ProductDetailedDTO} objects containing detailed product information
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    List<ProductDetailedDTO> findByUEN(String uen) throws ExecutionException, InterruptedException;

   /**
     * Finds products based on a product ID and the user's postal code, returning details of each matching product.
     * 
     * @param pid the product ID to search for
     * @param userPostalCode the postal code of the user, used to calculate distance 
     * @return a list of {@link WholesalerProductDetailsDTO} containing product details and location information
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    List<WholesalerProductDetailsDTO> findByPid(String pid, String userPostalCode) throws ExecutionException, InterruptedException, IOException;

    /**
     * Retrieves a product by its unique ID (swp_id).
     * 
     * @param swp_id the unique wholesaler product identifier
     * @return a {@link WholesalerProductDTO} containing product details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    WholesalerProductDTO findBySwp_id(String swp_id) throws ExecutionException, InterruptedException;

    /**
     * Retrieves the name of a product based on the wholesaler product ID (swp_id).
     * 
     * @param swp_id the unique wholesaler product identifier
     * @return the name of the product as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    String getWholesalerProductName(String swp_id) throws ExecutionException, InterruptedException;

     /**
     * Retrieves the description of a product based on its unique ID (swp_id).
     * 
     * @param swp_id the unique wholesaler product identifier
     * @return the description of the product as a {@link String}
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    String getWholesalerProductDesc(String swp_id) throws ExecutionException, InterruptedException;

    /**
     * Retrieves the image URL of a product based on its unique ID (swp_id).
     * 
     * @param swpId the unique wholesaler product identifier
     * @return the image URL of the product as a {@link String}
     * @throws ExecutionException
     * @throws InterruptedException
     */
    String getWholesalerProductImg(String swpId)throws ExecutionException, InterruptedException;

    /**
     * Adds a new product to the database for a wholesaler.
     * 
     * @param product a {@link WholesalerProductDTO} object containing details of the product to be added
     */
    void addWholesalerProduct(WholesalerProductDTO product);

    /**
     * Updates details of an existing product by its unique ID (swp_id).
     * 
     * @param id the unique wholesaler product identifier
     * @param updates a {@link Map} of fields and values to update
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    void updateWholesalerProduct(String id, Map<String, Object> updates) throws ExecutionException, InterruptedException;

    /**
     * Deletes a product from the database based on its unique ID.
     * 
     * @param id the unique product identifier
     */
    void deleteWholesalerProduct(String id);

    /**
     * Retrieves a product based on its product ID (pid) and the UEN of the wholesaler.
     * 
     * @param pid the product ID to search for
     * @param uen the unique entity number of the wholesaler
     * @return a {@link WholesalerProducts} object containing details of the product
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    WholesalerProducts getWProductByPIDandUEN(String pid, String uen) throws ExecutionException, InterruptedException;

}