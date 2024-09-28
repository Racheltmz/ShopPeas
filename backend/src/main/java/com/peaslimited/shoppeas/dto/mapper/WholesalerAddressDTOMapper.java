package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.WholesalerAddressDTO;
import com.peaslimited.shoppeas.model.WholesalerAddress;

public class WholesalerAddressDTOMapper {
    public static WholesalerAddressDTO toDTO(WholesalerAddress wholesalerAddress) {
        return new WholesalerAddressDTO(
            wholesalerAddress.getStreet_name(),
            wholesalerAddress.getUnit_no(),
            wholesalerAddress.getBuilding_name(),
            wholesalerAddress.getCity(),
            wholesalerAddress.getPostal_code()
        );
    }

    public static WholesalerAddress toEntity(WholesalerAddressDTO dto) {
        WholesalerAddress wholesalerAddress = new WholesalerAddress();
        wholesalerAddress.setStreet_name(dto.getStreet_name());
        wholesalerAddress.setUnit_no(dto.getUnit_no());
        wholesalerAddress.setBuilding_name(dto.getBuilding_name());
        wholesalerAddress.setCity(dto.getCity());
        wholesalerAddress.setPostal_code(dto.getPostal_code());
        return wholesalerAddress;
    }
}
