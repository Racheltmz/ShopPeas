package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.model.WholesalerProfile;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import com.peaslimited.shoppeas.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/wholesaler")
public class WholesalerController {

    @Autowired
    WholesalerService wholesalerService;

    @Autowired
    WholesalerAddressService wholesalerAddressService;

    @Autowired
    WholesalerAccountService wholesalerAccountService;

    @GetMapping
    @RequestMapping(value = "/{uid}")
    @ResponseStatus(code = HttpStatus.OK)
    public WholesalerProfile getWholesaler(@PathVariable("uid") String UID) throws ExecutionException, InterruptedException {
        WholesalerProfile profile = new WholesalerProfile();
        Wholesaler wholesaler = wholesalerService.getWholesaler(UID);
        String UEN = wholesaler.getUEN();
        profile.setWholesaler(wholesaler);
        profile.setWholesalerAddress(wholesalerAddressService.getWholesalerAddress(UEN));
        profile.setWholesalerAccount(wholesalerAccountService.getWholesalerAccount(UEN));
        return profile;
    }
}
