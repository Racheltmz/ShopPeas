package com.peaslimited.shoppeas.model;

import lombok.Data;

@Data
public class WholesalerProfile {
    private Wholesaler wholesaler;
    private WholesalerAccount wholesalerAccount;
    private WholesalerAddress wholesalerAddress;

    public Wholesaler getWholesaler() {
        return wholesaler;
    }

    public void setWholesaler(Wholesaler wholesaler) {
        this.wholesaler = wholesaler;
    }

    public WholesalerAccount getWholesalerAccount() {
        return wholesalerAccount;
    }

    public void setWholesalerAccount(WholesalerAccount wholesalerAccount) {
        this.wholesalerAccount = wholesalerAccount;
    }

    public WholesalerAddress getWholesalerAddress() {
        return wholesalerAddress;
    }

    public void setWholesalerAddress(WholesalerAddress wholesalerAddress) {
        this.wholesalerAddress = wholesalerAddress;
    }
}
