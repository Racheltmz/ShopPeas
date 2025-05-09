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

/**
 * This class handles operations surrounding payment, such as fetching a user's payment methods from FireBase,
 * adding a new payment method, and deleting a payment method.
 */
@CrossOrigin
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private ConsumerAccountService consumerAccService;

    /**
     * Gets the card numbers of all the cards belonging to a single consumer and is called from the frontend with
     * HTTP status "/payment/get".
     * @return A Map containing a list of the consumer's card numbers.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/get")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Object> getPayment() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        // ACTION: GET PAYMENTS
        ConsumerAccountDTO conAcc = consumerAccService.getConsumerAccount(uid);

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
            // cardNum = cardNum.substring(cardNum.length()-4);
            cardNumList.add(cardNum);
        }
        payments.put("card_numbers", cardNumList);

        return payments;
    }

    /**
     * Adds a new payment method for the consumer and is called from the frontend with HTTP status "/payment/add".
     * @param data A Map containing the consumer's name, card number, card expiry date, and cvv
     * @throws ExecutionException
     * @throws InterruptedException
     */
    //add payment
    @PostMapping("/add")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addPayment(@RequestBody Map<String, Object> data) throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();

        String card_no = data.get("card_no").toString();
        String expiry = data.get("expiry_date").toString();
        String cvv = data.get("cvv").toString();
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
            int newIndex = currPaymentMethodMap.size();
            String index= Integer.toString(newIndex);
            currPaymentMethodMap.put(index, paymentMap);
            Map<String, Object> newData = new HashMap<String, Object>();
            newData.put("paymentMtds", currPaymentMethodMap);
            consumerAccService.updateConsumerAccount(uid, newData);
        }
    }

    /**
     * Deletes a payment method and is called from the frontend with HTTP status "/payment/delete".
     * @param data A map containing the card number of the card that is to be deleted.
     * @throws ExecutionException
     * @throws InterruptedException
     */
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
        Map<String, Object> newPaymentMethodsMap = new HashMap<>();

        int newIndex = 0;
        for(int i = 0; i < paymentMethodsMap.size(); i++) {
            String index = Integer.toString(i);
            Map<String, Object> card = (Map<String, Object>) paymentMethodsMap.get(index);
            String cardNum = card.get("card_no").toString();

            if(!cardNum.equals(card_no)) {
                newPaymentMethodsMap.put(Integer.toString(newIndex), card);
                newIndex++;
            }
        }

        Map<String, Object> newData = new HashMap<String, Object>();
        newData.put("paymentMtds", newPaymentMethodsMap);
        consumerAccService.updateConsumerAccount(uid, newData);
    }

}