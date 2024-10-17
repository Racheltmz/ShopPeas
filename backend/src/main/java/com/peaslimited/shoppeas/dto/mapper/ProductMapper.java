package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.ProductDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.model.Product;


public class ProductMapper {
    // convert the product entity to a ProductDTO
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getName(), product.getPackage_size(), product.getImage_url());
    }

}
