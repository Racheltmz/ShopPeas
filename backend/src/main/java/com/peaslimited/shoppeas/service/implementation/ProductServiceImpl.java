package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.repository.ProductRepository;
import com.peaslimited.shoppeas.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.peaslimited.shoppeas.dto.mapper.ProductMapper;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    // get Product details
    public ProductDTO getProductById(String pid) throws ExecutionException, InterruptedException {
        return productRepository.findByPID(pid);
    }

    @Override
    public List<Product> getAllProducts() throws ExecutionException, InterruptedException{
        return productRepository.findAll();
    }

    @Override
    public void addProduct(String PID, ProductDTO product){
        productRepository.addByPID(PID, product);
    }

    @Override
    public void updateProduct(String PID, Map<String, Object> data) throws ExecutionException, InterruptedException {
        productRepository.updateByPID(PID,data);
    }

    @Override
    public Product findByProductName(String name) throws ExecutionException, InterruptedException
    {
        return productRepository.findByProductName(name);
    }

    @Override
    public String getImageURLByProductName(String productName) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = productRepository.getUrlByName(productName);
        return (document != null && document.contains("image_url")) ? document.getString("image_url") : null;
    }

}
