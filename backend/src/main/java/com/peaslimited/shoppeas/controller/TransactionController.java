package com.peaslimited.shoppeas.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.service.*;

import com.peaslimited.shoppeas.service.implementation.TransactionCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * This class handles operations surrounding transactions, such as fetching transactions from FireBase by wholesaler,
 * updating a transaction's status, and checking out the contents of a shopping cart.
 */
@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionsService transactionService;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private TransactionCacheServiceImpl transactionCacheService;

    /**
     * Fetches transaction records from FireBase by wholesaler and is called from the frontend with
     * HTTP status "/transaction/get".
     * @param uen String representing the wholesaler's UEN (Unique Entity Number).
     * @param status String representing the transaction status: PENDING-ACCEPTANCE, PENDING-COMPLETION, COMPLETED.
     * @return ArrayList<Object> of transaction records representing the transactions for a wholesaler of a specific
     * transaction type.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/get")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public ArrayList<Object> getTransactionsByWholesaler(@RequestParam String uen, @RequestParam String status) throws ExecutionException, InterruptedException {
        // check if UEN is valid
        if(!wholesalerService.UENExists(uen)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UEN Format");
        }
        // Check if status is valid
        if (!wholesalerService.isValidStatus(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }
        List<QueryDocumentSnapshot> docList = transactionService.getDocByUENAndStatus(uen, status);

        ArrayList<Object> transactionList = new ArrayList<>();

        DocumentSnapshot document = null;
        for (QueryDocumentSnapshot queryDocumentSnapshot : docList) {
            if (queryDocumentSnapshot != null) {
                Map<String, Object> dataMap = new HashMap<>();

                document = queryDocumentSnapshot;

                String uid = Objects.requireNonNull(document.get("uid")).toString();
                double total_price = Double.parseDouble(Objects.requireNonNull(document.get("total_price")).toString());
                dataMap.put("tid", document.getId());
                dataMap.put("uid", uid);

                if (document.contains("converted_price")) {
                    dataMap.put("total_price", Double.parseDouble(Objects.requireNonNull(document.get("converted_price")).toString()));
                    dataMap.put("currency", "MYR");
                } else {
                    dataMap.put("total_price", total_price);
                    dataMap.put("currency", "SGD");
                }
                dataMap.put("items", transactionService.getProductListfromTransaction(document, false));
                transactionList.add(dataMap);
            }
        }
        return transactionList;
    }

    /**
     * Updates a transaction's status, and is called from the frontend with HTTP status "/transaction/updatetransactionstatus".
     * @param data Map<String, Object> containing information about the transaction whose status is to be updated, such as TID
     *             (transaction ID), and the new status
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PatchMapping("/updatetransactionstatus")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateTransactionStatus(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        String tid = (String) data.get("tid");
        String status = (String) data.get("status");
        System.out.println(transactionCacheService.doesTransactionExist(tid));
        System.out.println(wholesalerService.isValidStatus(status));

        if (!transactionCacheService.doesTransactionExist(tid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid TID: Order not found");
        }
        if(!wholesalerService.isValidStatus(status)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }

        transactionService.updateTransactionStatus(data);
    }

    /**
     * Checks out the contents of a consumer's shopping cart, deletes the products in the shopping cart record,
     * updates the corresponding transaction statuses to "PENDING-ACCEPTANCE", and creates an order history record.
     * @param data Map<String, Object> containing the items in the shopping cart.
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     */
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void checkout(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        // Check cart items and enforce limits
        ArrayList<Object> cartItems = (ArrayList<Object>) data.get("cart_items");

        // Check that there is at least one order in cart
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart cannot be empty");
        }
        // Check max number of orders in cart
        if (cartItems.size() > 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many orders in cart. Maximum allowed is 4 ");
        }

        for (Object order : cartItems) {
            Map<String, Object> orderMap = (Map<String, Object>) order;
            // Validate wholesaler and uen fields
            String wholesaler = (String) orderMap.get("wholesaler");
            String uen = (String) orderMap.get("uen");

            if (wholesaler == null || wholesaler.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wholesaler cannot be empty");
            }
            if (uen == null || uen.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UEN cannot be empty");
            }

            // Check if the wholesaler and uen are valid and match each other
            if (!wholesalerService.isValidWholesalerAndUEN(wholesaler, uen)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid wholesaler or UEN does not match wholesaler");
            }

            ArrayList<Object> items = (ArrayList<Object>) orderMap.get("items");
            // Check order has at least 1 item
            if (items == null || items.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Each order must contain at least one item");
            }
            // Check max items per order
            if (items.size() > 5) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many items in order. Maximum allowed is 5");
            }
        }

        transactionService.updateToCheckout(uid, data);
    }
}