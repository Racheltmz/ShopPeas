package com.peaslimited.shoppeas.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;
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

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private WholesalerTransactionsService wholesalerTransactionService;

    @Autowired
    private TransactionsService transactionService;

    @Autowired
    private WholesalerService wholesalerService;

    @Autowired
    private TransactionCacheServiceImpl transactionCacheService;

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

    @PatchMapping("/updatetransactionstatus")
    @PreAuthorize("hasRole('WHOLESALER')")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateTransactionStatus(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        String tid = (String) data.get("tid");
        String status = (String) data.get("status");

        if (!transactionCacheService.doesTransactionExist(tid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid TID: Order not found");
        }
        if(!wholesalerService.isValidStatus(status)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status");
        }

        transactionService.updateTransactionStatus(data);
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void checkout(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        transactionService.updateToCheckout(uid, data);
    }
}