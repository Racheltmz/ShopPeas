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

/**
 * CartController handles operations involving the shopping cart, such as getting cart contents, adding products to cart,
 * updating products in cart, and deleting a product from cart.
 */
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

    /**
     * Returns the contents of a consumer's shopping cart and is called from the frontend with
     * HTTP path "/cart/view".
     * @return Map<String, Object> containing the contents of a consumer's shopping cart.
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * Adds a single product to a consumer's shopping cart and is called from the frontend with
     * HTTP path "/cart/add".
     * @param data Map<String, Object> containing information about the product that will be added to cart, such as
     *             swp_id (wholesaler product id), quantity, wholesaler UEN, unit price of the product, and total price of the
     *             products being added to cart.
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws ResponseStatusException Thrown when product quantity is invalid (quantity <= 0) or when the wholesaler product
     * ID is invalid (swp_id), i.e., wholesaler product does not exist.
     */
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

    /**
     * Updates the quantity of an item in the shopping cart and is called from the frontend with
     * HTTP path "/cart/update".
     * @param data Map<String, Object> containing information about the product that will be updated in cart, such as
     *       swp_id (wholesaler product id), quantity, wholesaler UEN, and the unit price of the product
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * Deletes an item from the shopping cart and is called from the frontend with HTTP path "/cart/delete".
     * @param data Map<String, Object> containing information about the product that will be deleted from cart, such as
     *       swp_id (wholesaler product id) and wholesaler UEN
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PatchMapping("/deleteItem")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        cartService.deleteCartProduct(uid, data);
    }
}