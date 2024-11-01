package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.TransactionsService;
import com.peaslimited.shoppeas.service.WholesalerService;
import com.peaslimited.shoppeas.service.implementation.TransactionCacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransactionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class updatingTransactionStatusTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @MockBean
    private WholesalerService wholesalerService;



    @MockBean
    private TransactionCacheServiceImpl transactionCacheService;


    // test case 1: Updating valid tid and status = completed
    @Test
    public void updateTransactionStatus_ValidTidStatusCompleted() throws Exception {
        String validTID = "luUU3ZHTTLv7buR14nGO";
        String validStatus = "COMPLETED";

        // Create a JSON body to patch
        String requestBody = "{ \"tid\": \"" + validTID + "\", \"status\": \"" + validStatus + "\" }";

        // Mock the service
        Mockito.when(transactionCacheService.doesTransactionExist(validTID)).thenReturn(true);
        Mockito.when(wholesalerService.isValidStatus(validStatus)).thenReturn(true);
        Mockito.doNothing().when(transactionsService).updateTransactionStatus(Mockito.anyMap());

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    // test case 2: Update valid tid and status = PENDING-ACCEPTANCE
    @Test
    public void updateTranssactionStatus_ValidTidStatusPA() throws Exception{
        String validTID = "n4I3FkKeOX6G2l9BFyHu";
        String validStatus = "PENDING-ACCEPTANCE";

        // Create a JSON body to patch
        String requestBody = "{ \"tid\": \"" + validTID + "\", \"status\": \"" + validStatus + "\" }";

        // Mock the service
        Mockito.when(transactionCacheService.doesTransactionExist(validTID)).thenReturn(true);
        Mockito.when(wholesalerService.isValidStatus(validStatus)).thenReturn(true);
        Mockito.doNothing().when(transactionsService).updateTransactionStatus(Mockito.anyMap());

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    // test case 3: Valid Tid and status = IN-CART
    @Test
    public void updateTranssactionStatus_ValidTidStatusIC() throws Exception{
        String validTID = "41Mwt9l7y778UzlegiiS";
        String validStatus = "IN-CART";

        // Create a JSON body to patch
        String requestBody = "{ \"tid\": \"" + validTID + "\", \"status\": \"" + validStatus + "\" }";

        // Mock the service
        Mockito.when(transactionCacheService.doesTransactionExist(validTID)).thenReturn(true);
        Mockito.when(wholesalerService.isValidStatus(validStatus)).thenReturn(true);
        Mockito.doNothing().when(transactionsService).updateTransactionStatus(Mockito.anyMap());

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    // test case 4: Invalid TID (wrong format) and valid status
    @Test
    public void updateTransactionStatus_InvalidTid() throws Exception {
        String TID = "12345";
        String validStatus = "COMPLETED";

        // Create a JSON body to patch
        String requestBody = "{ \"tid\": \"" + TID + "\", \"status\": \"" + validStatus + "\" }";

        // Mock the service
        Mockito.when(transactionCacheService.doesTransactionExist("12345")).thenReturn(false); // Mock invalid TID check

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }

    // test case 5: Invalid TID (empty) and valid status
    @Test
    public void updateTransactionStatus_EmptyTid() throws Exception {
        String TID = "";
        String validStatus = "COMPLETED";

        // Create a JSON body to patch
        String requestBody = "{ \"tid\": \"" + TID + "\", \"status\": \"" + validStatus + "\" }";

        // Mock the service
        Mockito.when(transactionCacheService.doesTransactionExist(TID)).thenReturn(false); // Mock invalid TID check

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }

    // Test case 6: Valid TID, invalid status
    @Test
    public void updateTransactionStatus_invalidStatus() throws Exception {
        String validTID = "41Mwt9l7y778UzlegiiS";
        String validStatus = "in-cart";

        // Create a JSON body to patch
        String requestBody = "{ \"tid\": \"" + validTID + "\", \"status\": \"" + validStatus + "\" }";

        // Mock the service
        Mockito.when(transactionCacheService.doesTransactionExist(validTID)).thenReturn(true);
        Mockito.doNothing().when(transactionsService).updateTransactionStatus(Mockito.anyMap());

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    // Test case 7: Null TID + Valid Status
    @Test
    public void updateTransactionStatus_NullTid() throws Exception {
        String validStatus = "COMPLETED";

        // Create a JSON body with null TID
        String requestBody = "{ \"tid\": null, \"status\": \"" + validStatus + "\" }";

        mockMvc.perform(MockMvcRequestBuilders.patch("/transaction/updatetransactionstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}
