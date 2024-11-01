package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WholesalerProductService wholesalerProductService;

    @GetMapping("/view")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getCart() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        Map<String, Object> returnMap = cartService.getCartByUID(uid);
        if (returnMap == null) {
            returnMap = new HashMap<>();
        }
        return returnMap;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addToCart(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException, ResponseStatusException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());

        if(wholesalerProductService.getBySwp_id(swp_id) == null)
        {
            System.out.println("Error! 2");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Wholesaler product does not exist");

        }
        else if (quantity <= 0) {
            System.out.println("Error! 1");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Invalid quantity");
            //wholesalerProductCacheService.doesTransactionExist(swp_id) == false
        }

        cartService.addCartItem(uid, data);
    }

    // update quantity
    @PatchMapping("/update")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateCart(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        String uen = data.get("uen").toString();
        String swp_id = data.get("swp_id").toString();

        if(wholesalerService.UENExists(uen) == false)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Wholesaler does not exist");
        }
        System.out.println(swp_id);
        if(wholesalerProductService.getBySwp_id(swp_id) == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Wholesaler product does not exist");
        }
        String cid = cartRepository.findCIDByUID(uid);
        if(cid == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Cart does not exist");
        }

        cartService.updateCartQuantity(uid, data);
    }

    @PatchMapping("/deleteItem")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        cartService.deleteCartProduct(uid, data);
    }
}