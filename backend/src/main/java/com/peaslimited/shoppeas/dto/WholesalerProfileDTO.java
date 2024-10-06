package com.peaslimited.shoppeas.dto;

import com.peaslimited.shoppeas.model.Wholesaler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerProfileDTO {
    private Wholesaler wholesaler;
    private WholesalerAccountDTO wholesalerAccount;
    private WholesalerAddressDTO wholesalerAddress;
}
