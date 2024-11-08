package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.TransactionsService;
import com.peaslimited.shoppeas.service.WholesalerService;
import com.peaslimited.shoppeas.service.implementation.TransactionCacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the updateTransactionStatus functionality in TransactionController.
 * using black-box testing on the `/transaction/updatetransactionstatus` endpoint, 
 * verifying the behavior for various valid and invalid TID and status values.
 */
@WebMvcTest(controllers = TransactionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class updatingTransactionStatusTest {

    /**
     * Sets up the MockMvc for performing HTTP requests in the tests.
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @MockBean
    private WholesalerService wholesalerService;



    @MockBean
    private TransactionCacheServiceImpl transactionCacheService;


    /**
     * Test case 1: Updating transaction status with a valid TID and status = "COMPLETED".
     * Expects a 200 OK status if the transaction is updated successfully.
     *
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Test case 2: Updating transaction status with a valid TID and status = "PENDING-ACCEPTANCE".
     * Expects a 200 OK status if the transaction is updated successfully.
     *
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Test case 3: Updating transaction status with a valid TID and status = "IN-CART".
     * Expects a 200 OK status if the transaction is updated successfully.
     *
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Test case 4: Attempting to update transaction status with an invalid TID format.
     * Expects a 400 Bad Request status due to the invalid TID.
     *
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Test case 5: Attempting to update transaction status with an empty TID.
     * Expects a 400 Bad Request status due to the empty TID.
     *
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Test case 6: Attempting to update transaction status with a valid TID but an invalid status.
     * Expects a 400 Bad Request status due to the invalid status value.
     *
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Test case 7: Attempting to update transaction status with a null TID and a valid status.
     * Expects a 400 Bad Request status due to the null TID.
     *
     * @throws Exception if the request fails unexpectedly
     */
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
