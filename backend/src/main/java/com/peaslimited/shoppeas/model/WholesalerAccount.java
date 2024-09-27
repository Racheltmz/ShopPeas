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
    private Long bank_account_no;

    public WholesalerAccount(String bank, String bank_account_name, Long bank_account_no) {
        this.bank = bank;
        this.bank_account_name = bank_account_name;
        this.bank_account_no = bank_account_no;
    }

    public String getBank() {
        return bank;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public Long getBank_account_no() {
        return bank_account_no;
    }
}
