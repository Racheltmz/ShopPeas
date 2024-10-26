package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.ConsumerAccountDTO;
import com.peaslimited.shoppeas.service.ConsumerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private ConsumerAccountService consumerAccService;

    //get payment methods
    @SuppressWarnings("unchecked")
    @GetMapping("/get")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getPayment() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        System.out.println("hi");
        // ACTION: GET PAYMENTS
        ConsumerAccountDTO conAcc = consumerAccService.getConsumerAccount(uid);
        System.out.println(conAcc);

        Map<String,Object> paymentMethodsMap = conAcc.getPaymentMtds();
        // for returning
        Map<String, Object> payments = new HashMap<>();
        ArrayList<String> cardNumList = new ArrayList<>();

        // ACTION: CONVERT TO MAP
        for (int i = 0; i < paymentMethodsMap.size(); i++) {
            Map<String, Object> card = new HashMap<>();
            String index = Integer.toString(i);
            card = (Map<String, Object>) paymentMethodsMap.get(index);
            String cardNum = card.get("card_no").toString();
            // get first four digits from card number
            cardNum = cardNum.substring(cardNum.length()-4);
            cardNumList.add(cardNum);
        }
        payments.put("card_numbers", cardNumList);

        return payments;

    }

    //add payment
    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addPayment(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        Long card_no = Long.parseLong(data.get("card_no").toString());
        String expiry = data.get("expiry_date").toString();
        Integer cvv = Integer.parseInt(data.get("cvv").toString());
        String name = data.get("name").toString();

        //ACTION: ADD CARD TO CONSUMER ACCOUNT PAYMENT METHOD LIST AND FIREBASE
        Map<String, Object> paymentMap = new HashMap<String, Object>();
        paymentMap.put("card_no", card_no);
        paymentMap.put("expiry", expiry);
        paymentMap.put("cvv", cvv);
        paymentMap.put("name", name);


        //check if consumer has a consumer account entry
        ConsumerAccountDTO consumerAcc = consumerAccService.getConsumerAccount(uid);
        if(consumerAcc == null) // consumer account entry DNE
        {
            //ACTION: CREATE CONSUMER ACC ENTRY
            //nested list
            Map<String, Object> paymentMethodsMap =  new HashMap<>();
            paymentMethodsMap.put("0", paymentMap);
            ConsumerAccountDTO newConsumerAcc = new ConsumerAccountDTO(paymentMethodsMap);
            consumerAccService.addConsumerAccount(uid, newConsumerAcc);
        }
        else // consumer account entry exists
        {
            //ACTION: UPDATE PAYMENT METHOD LIST IN EXISTING CONSUMER ACC
            Map<String, Object> currPaymentMethodMap = consumerAcc.getPaymentMtds();
            int newIndex = currPaymentMethodMap.size() - 1;
            String index= Integer.toString(newIndex);
            currPaymentMethodMap.put(index, paymentMap);
            Map<String, Object> newData = new HashMap<String, Object>();
            newData.put("paymentMtds", currPaymentMethodMap);
            consumerAccService.updateConsumerAccount(uid, newData);
        }
    }

    //delete payment
    @PatchMapping("/delete")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePayment(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        String card_no = data.get("card_no").toString();

        // ACTION: GET PAYMENTS
        ConsumerAccountDTO conAcc = consumerAccService.getConsumerAccount(uid);
        Map<String, Object> paymentMethodsMap = conAcc.getPaymentMtds();

        for(int i = 0; i < paymentMethodsMap.size(); i++) {
            Map<String, Object> card = new HashMap<>();
            String index = Integer.toString(i);
            card = (Map<String, Object>) paymentMethodsMap.get(index);
            String cardNum = card.get("card_no").toString();
            if(cardNum.equals(card_no)) //ACTION: REMOVE MAP
            {
                paymentMethodsMap.remove(i);
                break;
            }
        }
        //ACTION: UPDATE CONSUMER ACC
        Map<String, Object> newData = new HashMap<String, Object>();
        newData.put("paymentMtds", paymentMethodsMap);
        consumerAccService.updateConsumerAccount(uid, newData);
    }

}