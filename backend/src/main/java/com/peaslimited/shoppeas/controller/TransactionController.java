package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.service.CurrencyService;
import com.peaslimited.shoppeas.service.ShoppingCartService;
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
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WholesalerTransactionsService wholesalerTransactionService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private CartController cartController;

    // CONSUMER METHODS

    //TODO: @saffron get order history, make payment



    
    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void makePayment(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException, ExecutionException, InterruptedException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        //ACTION: GET TRANSACTION DATA
        //convert order data to array
        String orderStr = data.get("orders").toString();
        orderStr = orderStr.replace("[","");
        orderStr = orderStr.replace("]","");
        ArrayList<String> orders = new ArrayList<String>(Arrays.asList(orderStr.split(",")));

        //remainder of transaction data
        String tid = data.get("tid").toString();
        double price = Double.parseDouble(data.get("price").toString());
        //String status = data.get("status").toString();
        String status = "PENDING-ACCEPTANCE";
        String date = data.get("date").toString();

        //ACTION: ADDS TRANSACTION RECORD
        WholesalerTransactionsDTO transaction = WholesalerTransactionMapper.toWTransactionDTO(uid, orders, price, date, status);
        wholesalerTransactionService.addWTransaction(tid, transaction);

        // ACTION: only if the wholesaler's selected currency is MYR, will the currency api be invoked
        // shifted above
        //double price = Double.parseDouble(data.get("price").toString());
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

        //ACTION: empty shopping cart
        String cid = cartController.getCID(uid);
        cartService.deleteWholeCart(cid);

        /*
        -> get specific payment method from API call
        -> find payment method
        -> if exists, return true (payment success)
        assume by default that all payments are successful



         */


    }



    //TODO: @saffron WHOLESALER METHODS
    
    //get all transactions

    
}
