package com.peaslimited.shoppeas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerAccount {
    @Id
    private String UEN;
    private String bank;
    private String bank_account_name;
    private String bank_account_no;

}
