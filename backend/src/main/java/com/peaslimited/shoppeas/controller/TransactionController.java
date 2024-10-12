package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.CurrencyService;
import com.peaslimited.shoppeas.service.WholesalerTransactionsService;
import com.peaslimited.shoppeas.dto.mapper.WholesalerTransactionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WholesalerTransactionsService wholesalerTransactionService;

    // CONSUMER METHODS

    //get order history

    //get cart

    //add to cart

    //get all payment methods

    //add payment
    
    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void makePayment(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        //TODO: @saffron this part is the code to create the transaction and order records
        // convert order data to array
        String orderStr = data.get("orders").toString();
        orderStr = orderStr.replace("[","");
        orderStr = orderStr.replace("]","");
        ArrayList<String> orders = new ArrayList<String>(Arrays.asList(orderStr.split(",")));
        
        String tid = data.get("tid").toString();
        double total_price = Double.parseDouble(data.get("total_price").toString());
        String status = data.get("status").toString();
        LocalDateTime date = LocalDateTime.now();
        
        wholesalerTransactionService.addWTransaction(tid, WholesalerTransactionMapper.toWTransactionDTO(uid, orders, total_price, date, status));

        // TODO: only if the wholesaler's selected currency is MYR, will the currency api be invoked
        double price = Double.parseDouble(data.get("price").toString());
        String preferredCurrency = data.get("currency").toString();
        double exchangeRate = 0.0;
        double finalPrice = 0.0;

        if (preferredCurrency.equals("MYR")) {
            // NOTE: I didn't return anything for now but feel free to change accordingly
            exchangeRate = currencyService.exchangeRate(price, preferredCurrency);
            finalPrice = price * exchangeRate;
            // NOTE: temporary print statement to validate output
            System.out.println(finalPrice);
        }
    }


    //WHOLESALER METHODS

    //view transactions and order and buyer details
    
    //get transactions by type

    //get to be accepted, get to be completed, get completed

    //add to be accepted transaction (after make payment)

    //add to be completed transaction (after accepted)

    //add completed transaction (after completing transaction)
    
}
