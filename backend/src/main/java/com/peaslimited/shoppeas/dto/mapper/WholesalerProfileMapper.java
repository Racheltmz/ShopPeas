package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.dto.WholesalerDTO;
import com.peaslimited.shoppeas.dto.WholesalerProfileDTO;

public class WholesalerProfileMapper {

    public static WholesalerProfileDTO toProfileDTO(WholesalerDTO wholesaler,
                                                    WholesalerAddressDTO wholesalerAddress,
                                                    WholesalerAccountDTO wholesalerAccount) {
        return new WholesalerProfileDTO(wholesaler, wholesalerAccount, wholesalerAddress);
    }
}
