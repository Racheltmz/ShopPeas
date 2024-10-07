package com.peaslimited.shoppeas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerProfileDTO {
    private WholesalerDTO wholesaler;
    private WholesalerAccountDTO wholesalerAccount;
    private WholesalerAddressDTO wholesalerAddress;
}
