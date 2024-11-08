package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;

import java.util.Map;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This interface handles operations related to the wholesaler product functionality, such as
 * retrieving, adding, and updating wholesaler products.
 */
public interface WholesalerProductService {

    /**
     * Get wholesaler products by PID.
     * @param pid Product ID.
     * @param uid Consumer's User ID.
     * @return list of wholesaler product information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws IOException If there is an I/O error.
     */
    List<WholesalerProductDetailsDTO> findByPid(String pid, String uid) throws ExecutionException, InterruptedException, IOException;

    /**
     * Get the products by wholesaler UID.
     * @param uid Wholesaler's UID.
     * @return List of Product details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    List<ProductDetailedDTO> getByWholesalerUID(String uid) throws ExecutionException, InterruptedException;

    /**
     * List of products by wholesaler UEN.
     * @param uen Wholesaler's UEN.
     * @return List of Product details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    List<ProductDetailedDTO> getByWholesalerUEN(String uen) throws ExecutionException, InterruptedException;

    /**
     * Get wholesaler product by SWPID.
     * @param swp_id Wholesaler product ID.
     * @return Wholesaler product information
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    WholesalerProductDTO getBySwp_id(String swp_id) throws ExecutionException, InterruptedException;

    /**
     * Add wholesaler product.
     * @param product Wholesaler product information.
     */
    void addWholesalerProduct(WholesalerProductDTO product);

    /**
     * Update wholesaler product.
     * @param swp_id Wholesaler product ID.
     * @param data Updates to make to the product.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    void updateWholesalerProduct(String swp_id, Map<String, Object> data) throws ExecutionException, InterruptedException;

    /**
     * Delete wholesaler product by SWPID.
     * @param swp_id Wholesaler product ID.
     */
    void deleteWholesalerProduct(String swp_id);

    /**
     * Get wholesaler product name by SWPID.
     * @param swp_id Wholesaler product ID.
     * @return Wholesaler product name.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    String getWholesalerProductName(String swp_id) throws ExecutionException, InterruptedException;

}
