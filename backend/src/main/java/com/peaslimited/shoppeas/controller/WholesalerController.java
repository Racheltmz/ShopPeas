package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.dto.mapper.WholesalerMapper;
import com.peaslimited.shoppeas.model.Product;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Autowired
    private WholesalerProductService wholesalerProductService;

    @GetMapping("/view/{uen}")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public WholesalerDetailsDTO viewWholesalerConsumer(@PathVariable("uen") String UEN) throws ExecutionException, InterruptedException {
        WholesalerDTO wholesaler = wholesalerService.getWholesalerUID(UEN);
        WholesalerAddressDTO wholesalerAddress = wholesalerAddressService.getWholesalerAddress(UEN);
        List<ProductDetailedDTO> wholesalerProducts = wholesalerProductService.getByWholesalerUEN(UEN);
        return WholesalerMapper.toDetailsDTO(wholesaler, wholesalerAddress, wholesalerProducts);
    }

    /**
     * Get wholesaler details by UID
     * @return wholesaler details
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public WholesalerProfileDTO viewWholesaler() throws ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        WholesalerDTO wholesaler = wholesalerService.getWholesaler(uid);
        String UEN = wholesaler.getUEN();
        WholesalerAddressDTO wholesalerAddress = wholesalerAddressService.getWholesalerAddress(UEN);
        WholesalerAccountDTO wholesalerAccount = wholesalerAccountService.getWholesalerAccount(UEN);

        // Invoke mapper to combine information and return the profile DTO
        return WholesalerMapper.toProfileDTO(wholesaler, wholesalerAddress, wholesalerAccount);
    }

    @PatchMapping("/profile/update")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateWholesaler(@RequestBody Map<String, Map<String, Object>> data) throws ExecutionException, InterruptedException, FirebaseAuthException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        Map<String, Object> profileDetails = data.get("wholesaler");
        Map<String, Object> accountDetails = data.get("wholesalerAccount");
        Map<String, Object> addressDetails = data.get("wholesalerAddress");
        authService.updateWholesaler(uid, profileDetails);
        String UEN = wholesalerService.updateWholesaler(uid, profileDetails);
        wholesalerAccountService.updateWholesalerAccount(UEN, accountDetails);
        wholesalerAddressService.updateWholesalerAddress(UEN, addressDetails);
    }

    @GetMapping("/rating/{uen}")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public RatingDTO getRating(@PathVariable("uen") String UEN) throws ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        assert uid != null;

        return wholesalerService.getRatingByUEN(UEN);
    }

    @PatchMapping("/rate")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void rateWholesaler(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        assert uid != null;

        String uen = data.get("uen").toString();
        String tid = data.get("tid").toString();
        Integer new_rating = Integer.valueOf(data.get("rating").toString());
        wholesalerService.addRating(uen, tid, new_rating);
    }
}
