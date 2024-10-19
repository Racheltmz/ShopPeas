package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerAccountDTO {
    private String bank;
    private String bank_account_name;
    private String bank_account_no;
}
