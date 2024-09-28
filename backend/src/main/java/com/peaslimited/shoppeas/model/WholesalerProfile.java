package com.peaslimited.shoppeas.model;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import lombok.Data;

@Data
public class WholesalerProfile {
    private Wholesaler wholesaler;
    private WholesalerAccountDTO wholesalerAccount;
    private WholesalerAddressDTO wholesalerAddress;
}
