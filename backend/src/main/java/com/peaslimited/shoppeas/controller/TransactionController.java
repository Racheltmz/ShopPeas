package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void makePayment(@RequestBody Map<String, Object> data) throws IOException, URISyntaxException {
        // Get UID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        // TODO: @saffron this part is the code to create the transaction and order records

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
}
