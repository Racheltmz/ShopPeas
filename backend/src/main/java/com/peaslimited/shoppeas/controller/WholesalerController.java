package com.peaslimited.shoppeas.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.dto.mapper.WholesalerMapper;
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

/**
 * Controller for managing wholesaler operations such as viewing,updating profiles, 
 * and handling ratings.
 */
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

    /**
     * Retrieves Wholesaler details by UEN for consumers and is called by the front end with 
     * a HTTP request of "wholesaler/view/{uen}"
     * @param UEN the uen of the wholesaler 
     * @return {@link WholesalerDetailsDTO} containing wholesaler details, address, and products
     * @throws ExecutionException
     * @throws InterruptedException
     */
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
     * Retrieves wholesaler details by UID and is called by the front end with 
     * a HTTP request of "wholesaler/profile"
     * @return {@link WholesalerProfileDTO} containing profile information, address, and account details
     * @throws ExecutionException
     * @throws InterruptedException
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

    /**
     * Updates wholesaler profile, account or address details and is called by the front end 
     * with a HTTP request of "wholesaler/profile/update"
     * @param data a Map <String, Map <String, Object> containing information about the wholesaler, 
     * wholesaler account and wholesaler address
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws FirebaseAuthException
     * 
     */
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

    /**
     * Retrieves the rating of a specific wholesaler by UEN and is called by the front end with 
     * a request of "wholesaler/rating/{uen}"
     * @param UEN the uen of the wholesaler 
     * @return {@link RatingDTO} containing rating information for the wholesaler
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/rating/{uen}")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public RatingDTO getRating(@PathVariable("uen") String UEN) throws ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        assert uid != null;

        return wholesalerService.getRatingByUEN(UEN);
    }

    /**
     * Allows a consumer to rate a wholesaler and is called by the front end 
     * with a request of "wholesaler/rate"
     * @param data a Map <String, Object containing information about the uen, tid and rating 
     * @throws ExecutionException
     * @throws InterruptedException
     */

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
