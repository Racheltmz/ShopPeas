package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing the bank account details of a wholesaler, such as
 * the wholesaler's bank details, including the bank name, account holder name, and account number.
 * It utilizes Lombok annotations to automatically generate getters, setters, toString, equals, hashCode, and constructors.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerAccountDTO {
    /**
     * The name of the bank where the wholesaler holds an account.
     */
    private String bank;
    /**
     * The name of the account holder as it appears on the bank account.
     */
    private String bank_account_name;
    /**
     * The bank account number of the wholesaler.
     */
    private String bank_account_no;
}
