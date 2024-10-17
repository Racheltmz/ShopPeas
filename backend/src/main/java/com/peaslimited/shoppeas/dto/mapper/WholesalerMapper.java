package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.model.Product;

import java.util.List;

public class WholesalerMapper {

    public static WholesalerDetailsDTO toDetailsDTO(WholesalerDTO wholesaler,
                                                    WholesalerAddressDTO wholesalerAddress,
                                                    List<Product> wholesalerProducts) {
        return new WholesalerDetailsDTO(wholesaler, wholesalerAddress, wholesalerProducts);
    }

    public static WholesalerProfileDTO toProfileDTO(WholesalerDTO wholesaler,
                                                    WholesalerAddressDTO wholesalerAddress,
                                                    WholesalerAccountDTO wholesalerAccount) {
        return new WholesalerProfileDTO(wholesaler, wholesalerAccount, wholesalerAddress);
    }

    public static WholesalerProductDetailsDTO toWholesalerProfileDTO(String wholesaler,
                                                                     String uen,
                                                                     String location,
                                                                     int stock,
                                                                     double price,
                                                                     double ratings) {
        return new WholesalerProductDetailsDTO(wholesaler, uen, location, stock, price, ratings);
    }
}
