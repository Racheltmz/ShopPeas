package com.peaslimited.shoppeas.dto;

import com.peaslimited.shoppeas.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerDetailsDTO {
    private WholesalerDTO wholesaler;
    private WholesalerAddressDTO wholesalerAddress;
    private List<Product> wholesalerProducts;
}
