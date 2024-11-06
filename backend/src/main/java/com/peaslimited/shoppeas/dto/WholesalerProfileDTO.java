package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the profile of a wholesaler.
 * This class encapsulates the wholesaler's core business information, account details,
 * and address, which are essential for managing the wholesaler's profile in the system.
 * The class utilizes Lombok annotations to automatically generate getter, setter, toString, equals, hashCode,
 * as well as constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerProfileDTO {
    /**
     * The basic information about the wholesaler as found in {@link WholesalerDTO}.
     * This object contains key details such as the wholesaler's name, UEN, email, phone number, and rating.
     */
    private WholesalerDTO wholesaler;
    /**
     * The account information of the wholesaler as found in {@link WholesalerAccountDTO}.
     * This includes banking details such as the bank name, account holder name, and account number.
     */
    private WholesalerAccountDTO wholesalerAccount;
    /**
     * The address of the wholesaler as found in {@link WholesalerAddressDTO}.
     * This object contains information about the wholesaler's physical location, including the street, city, and postal code.
     */
    private WholesalerAddressDTO wholesalerAddress;
}
