package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing detailed information about a wholesaler such as
 * the wholesaler's core details, address, and the list of products they offer.
 * The class utilizes Lombok annotations to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerDetailsDTO {
    /**
     * The basic details of the wholesaler as found in {@link WholesalerDTO}.
     * This object contains information such as the wholesaler's name, contact information,
     * and business-specific identifiers (e.g., ID, registration number).
     */
    private WholesalerDTO wholesaler;
    /**
     * The address of the wholesaler as found in {@link WholesalerAddressDTO}.
     * This object contains information about the wholesaler's physical location, including the street, city, and postal code.
     */
    private WholesalerAddressDTO wholesalerAddress;
    /**
     * A list of products offered by the wholesaler.
     * Each product in this list is represented by a {@link ProductDetailedDTO} object.
     */
    private List<ProductDetailedDTO> wholesalerProducts;
}
