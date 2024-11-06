package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.*;

import java.util.List;


/**
 * Utility class responsible for mapping `WholesalerDTO`, `WholesalerAddressDTO`,
 * `WholesalerAccountDTO`, and `ProductDetailedDTO` objects to their respective
 * detail and profile DTO representations.
 */
public class WholesalerMapper {

    /**
     * Maps a {@link WholesalerDTO}, {@link WholesalerAddressDTO}, and a list of {@link ProductDetailedDTO}
     * to a {@link WholesalerDetailsDTO} containing the wholesaler's details, address, and products.
     *
     * @param wholesaler The `WholesalerDTO` containing the basic information about the wholesaler.
     * @param wholesalerAddress The `WholesalerAddressDTO` containing the address of the wholesaler.
     * @param wholesalerProducts A list of `ProductDetailedDTO` objects representing the wholesaler's products.
     * @return A `WholesalerDetailsDTO` combining the wholesaler's information, address, and products.
     */
    public static WholesalerDetailsDTO toDetailsDTO(WholesalerDTO wholesaler,
                                                    WholesalerAddressDTO wholesalerAddress,
                                                    List<ProductDetailedDTO> wholesalerProducts) {
        return new WholesalerDetailsDTO(wholesaler, wholesalerAddress, wholesalerProducts);
    }

    /**
     * Maps a {@link WholesalerDTO}, {@link WholesalerAddressDTO}, and {@link WholesalerAccountDTO}
     * to a {@link WholesalerProfileDTO} containing the wholesaler's profile information.
     *
     * @param wholesaler The `WholesalerDTO` containing the basic wholesaler information.
     * @param wholesalerAddress The `WholesalerAddressDTO` containing the address of the wholesaler.
     * @param wholesalerAccount The `WholesalerAccountDTO` containing the wholesaler's banking information.
     * @return A `WholesalerProfileDTO` combining the wholesaler's information, account, and address.
     */
    public static WholesalerProfileDTO toProfileDTO(WholesalerDTO wholesaler,
                                                    WholesalerAddressDTO wholesalerAddress,
                                                    WholesalerAccountDTO wholesalerAccount) {
        return new WholesalerProfileDTO(wholesaler, wholesalerAccount, wholesalerAddress);
    }

    /**
     * Maps product-related data such as product ID, wholesaler name, location, price,
     * and ratings into a {@link WholesalerProductDetailsDTO}.
     * This DTO represents detailed information about a specific product offered by a wholesaler.
     *
     * @param swp_id The unique identifier (wholesaler product id) for the product offered by the wholesaler.
     * @param wholesaler The name of the wholesaler.
     * @param uen The Unique Entity Number (UEN) of the wholesaler.
     * @param location The location of the wholesaler.
     * @param postal_code The postal code of the wholesaler’s location.
     * @param duration The time taken to travel between the wholesaler and consumer.
     * @param distance The distance from the consumer to the wholesaler's location.
     * @param stock The current stock level of the product available.
     * @param price The price of the product.
     * @param ratings The average rating of the product.
     * @return A `WholesalerProductDetailsDTO` object containing detailed information about a specific product.
     */
    public static WholesalerProductDetailsDTO toWholesalerProfileDTO(String swp_id,
                                                                     String wholesaler,
                                                                     String uen,
                                                                     String location,
                                                                     String postal_code,
                                                                     int duration,
                                                                     double distance,
                                                                     int stock,
                                                                     double price,
                                                                     double ratings) {
        return new WholesalerProductDetailsDTO(swp_id, wholesaler, uen, location, postal_code, duration, distance, stock, price, ratings);
    }
}
