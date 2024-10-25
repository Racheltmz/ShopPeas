package com.peaslimited.shoppeas.dto.mapper;

import com.peaslimited.shoppeas.dto.WholesalerAccountDTO;
import com.peaslimited.shoppeas.model.WholesalerAccount;

public class WholesalerAccountDTOMapper {
    public static WholesalerAccountDTO toDTO(WholesalerAccount wholesalerAccount) {
        return new WholesalerAccountDTO(
                wholesalerAccount.getBank(),
                wholesalerAccount.getBank_account_name(),
                wholesalerAccount.getBank_account_no()
        );
    }

    public static WholesalerAccount toEntity(WholesalerAccountDTO dto) {
        WholesalerAccount wholesalerAccount = new WholesalerAccount();
        wholesalerAccount.setBank(dto.getBank());
        wholesalerAccount.setBank_account_name(dto.getBank_account_name());
        wholesalerAccount.setBank_account_no(dto.getBank_account_no());
        return wholesalerAccount;
    }
}
