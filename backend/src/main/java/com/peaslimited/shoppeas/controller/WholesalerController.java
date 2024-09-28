package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.model.Wholesaler;
import com.peaslimited.shoppeas.model.WholesalerProfile;
import com.peaslimited.shoppeas.service.AuthService;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import com.peaslimited.shoppeas.service.WholesalerAddressService;
import com.peaslimited.shoppeas.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/wholesaler")
public class WholesalerController {

    @Autowired
    private AuthService authService;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private WholesalerAddressService wholesalerAddressService;

    @Autowired
    private WholesalerAccountService wholesalerAccountService;

    /**
     * Get wholesaler details by UID
     * @param UID
     * @return wholesaler details
     * @throws ExecutionException
     * @throws InterruptedException
     */
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


    @PatchMapping
    @RequestMapping(value = "/update/{uid}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateWholesaler(@PathVariable("uid") String UID, @RequestBody Map<String, Map<String, Object>> data) throws ExecutionException, InterruptedException, FirebaseAuthException {
        Map<String, Object> profileDetails = data.get("wholesaler");
        Map<String, Object> accountDetails = data.get("wholesalerAccount");
        Map<String, Object> addressDetails = data.get("wholesalerAddress");
        authService.updateWholesaler(UID, profileDetails);
        String UEN = wholesalerService.updateWholesaler(UID, profileDetails);
        wholesalerAccountService.updateWholesalerAccount(UEN, accountDetails);
        wholesalerAddressService.updateWholesalerAddress(UEN, addressDetails);
    }
}
