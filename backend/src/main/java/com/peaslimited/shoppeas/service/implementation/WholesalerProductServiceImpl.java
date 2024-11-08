package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.ProductDetailedDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDetailsDTO;
import com.peaslimited.shoppeas.repository.ConsumerAddressRepository;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link WholesalerProductService} interface that handles operations related to wholesaler address functionality.
 * This service provides methods to retrieve, add, and update transaction items.
 */
@Service
public class WholesalerProductServiceImpl implements WholesalerProductService {

    /**
     * Repository for interacting with wholesaler data
     */
    @Autowired
    WholesalerRepository wholesalerRepository;

    /**
     * Repository for interacting with wholesaler product data
     */
    @Autowired
    WholesalerProductRepository wholesalerProductRepository;

    /**
     * Repository for interacting with consumer address data
     */
    @Autowired
    ConsumerAddressRepository consumerAddressRepository;

    /**
     * Get wholesaler products by PID.
     * @param pid Product ID.
     * @param uid Consumer's User ID.
     * @return list of wholesaler product information.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws IOException If there is an I/O error.
     */
    @Override
    public List<WholesalerProductDetailsDTO> findByPid(String pid, String uid) throws ExecutionException, InterruptedException, IOException {
        String userPostalCode = consumerAddressRepository.findByUID(uid).getPostal_code();
        return wholesalerProductRepository.findByPid(pid, userPostalCode);
    }

    /**
     * Get the products by wholesaler UID.
     * @param uid Wholesaler's UID.
     * @return List of Product details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public List<ProductDetailedDTO> getByWholesalerUID(String uid) throws ExecutionException, InterruptedException {
        String uen = wholesalerRepository.findByUID(uid).getUEN();
        return wholesalerProductRepository.findByUEN(uen);
    }

    /**
     * List of products by wholesaler UEN.
     * @param uen Wholesaler's UEN.
     * @return List of Product details.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public List<ProductDetailedDTO> getByWholesalerUEN(String uen) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.findByUEN(uen);
    }

    /**
     * Get wholesaler product by SWPID.
     * @param swp_id Wholesaler product ID.
     * @return Wholesaler product information
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public WholesalerProductDTO getBySwp_id(String swp_id) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.findBySwp_id(swp_id);
    }

    /**
     * Add wholesaler product.
     * @param wholesalerProductDTO Wholesaler product information.
     */
    @Override
    public void addWholesalerProduct(WholesalerProductDTO wholesalerProductDTO) {
        wholesalerProductRepository.addWholesalerProduct(wholesalerProductDTO);
    }

    /**
     * Update wholesaler product.
     * @param swp_id Wholesaler product ID.
     * @param updates Updates to make to the product.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public void updateWholesalerProduct(String swp_id, Map<String, Object> updates) throws ExecutionException, InterruptedException {
        wholesalerProductRepository.updateWholesalerProduct(swp_id, updates);
    }

    /**
     * Delete wholesaler product by SWPID.
     * @param swp_id Wholesaler product ID.
     */
    @Override
    public void deleteWholesalerProduct(String swp_id) {
        wholesalerProductRepository.deleteWholesalerProduct(swp_id);
    }

    /**
     * Get wholesaler product name by SWPID.
     * @param swp_id Wholesaler product ID.
     * @return Wholesaler product name.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    @Override
    public String getWholesalerProductName(String swp_id) throws ExecutionException, InterruptedException {
        return wholesalerProductRepository.getWholesalerProductName(swp_id);
    }

}
