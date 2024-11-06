package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.*;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.service.implementation.TransactionCacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import java.util.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest( value = TransactionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class getTransactionbyWholesalerTests {

    @Autowired
    private MockMvc mockMvc;

    // Mock all dependencies of TransactionController
    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private TransactionCacheServiceImpl transactionCacheService;

    @MockBean
    private WholesalerService wholesalerService;

    @MockBean
    private TransactionsService transactionService;

    @MockBean
    private WholesalerProductService wholesalerProductService;

    // test case 1: For getTransactionsByUEN, testing valid UEN and status = IN-CART
    @Test
    public void getValidTransaction_ValidUEN_StatusInCart() throws Exception {
        // Mocking QueryDocumentSnapshot to simulate Firebase data
        QueryDocumentSnapshot documentSnapshot = Mockito.mock(QueryDocumentSnapshot.class);

        // Define the behavior of the mocked document snapshot
        when(documentSnapshot.get("uid")).thenReturn("hnByvuE2t0fviOCA0q7T8nsqZVp1");
        when(documentSnapshot.get("total_price")).thenReturn(242.7);
        when(documentSnapshot.contains("converted_price")).thenReturn(false);
        when(documentSnapshot.getId()).thenReturn("41Mwt9l7y778UzlegiiS");

        // Create mock data for `items`
        ArrayList<Object> items = new ArrayList<>();
        items.add(Map.of("quantity", 5, "name", "Lim Traders Satay Chicken"));
        items.add(Map.of("quantity", 2, "name", "SSH CHILLI CRAB SAUCE PREMIUM"));

        // Mock `getProductListfromTransaction` to return the `items` array
        when(wholesalerService.UENExists("199203796C")).thenReturn(true);
        when(wholesalerService.isValidStatus("IN-CART")).thenReturn(true);
        when(transactionService.getProductListfromTransaction(documentSnapshot, false)).thenReturn(items);

        // Mock the transaction service to return a list containing this document snapshot
        List<QueryDocumentSnapshot> docList = Collections.singletonList(documentSnapshot);
        when(transactionService.getDocByUENAndStatus("199203796C", "IN-CART")).thenReturn(docList);

        // Perform the GET request and validate the response structure
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                        .param("uen", "199203796C")
                        .param("status", "IN-CART")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uid").value("hnByvuE2t0fviOCA0q7T8nsqZVp1"))
                .andExpect(jsonPath("$[0].total_price").value(242.7))
                .andExpect(jsonPath("$[0].currency").value("SGD")) // Assert the currency field
                .andExpect(jsonPath("$[0].items[0].quantity").value(5))
                .andExpect(jsonPath("$[0].items[0].name").value("Lim Traders Satay Chicken"))
                .andExpect(jsonPath("$[0].items[1].quantity").value(2))
                .andExpect(jsonPath("$[0].items[1].name").value("SSH CHILLI CRAB SAUCE PREMIUM"))
                .andExpect(jsonPath("$[0].tid").value("41Mwt9l7y778UzlegiiS"));
    }

    // test case 2: For getTransactionsByUEN, testing valid UEN and status = COMPLETED
    @Test
    public void getValidTransaction_ValidUEN_StatusCompleted() throws Exception {
        // Mocking QueryDocumentSnapshot to simulate Firebase data
        QueryDocumentSnapshot documentSnapshot = Mockito.mock(QueryDocumentSnapshot.class);

        // Define the behavior of the mocked document snapshot
        when(documentSnapshot.get("uid")).thenReturn("MDsubkeZsQS0kBpxte0Ncid1wg03");
        when(documentSnapshot.get("total_price")).thenReturn(142.54);
        when(documentSnapshot.contains("converted_price")).thenReturn(false);
        when(documentSnapshot.getId()).thenReturn("ZiKz4NgADn5g6IhBqILB");

        // Create mock data for `items`
        ArrayList<Object> items = new ArrayList<>();
        items.add(Map.of("quantity", 5, "name", "Meiji Low Fat Chocolate Flavour Milk"));
        items.add(Map.of("quantity", 3, "name", "Vitasoy Soya Bean Drink"));
        items.add(Map.of("quantity", 1, "name", "Win2 Potato Crisp Vegetable Flavour"));


        // Mock `getProductListfromTransaction` to return the `items` array
        when(wholesalerService.UENExists("201936456R")).thenReturn(true);
        when(wholesalerService.isValidStatus("COMPLETED")).thenReturn(true);
        when(transactionService.getProductListfromTransaction(documentSnapshot, false)).thenReturn(items);

        // Mock the transaction service to return a list containing this document snapshot
        List<QueryDocumentSnapshot> docList = Collections.singletonList(documentSnapshot);
        when(transactionService.getDocByUENAndStatus("201936456R", "COMPLETED")).thenReturn(docList);

        // Perform the GET request and validate the response structure
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                        .param("uen", "201936456R")
                        .param("status", "COMPLETED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uid").value("MDsubkeZsQS0kBpxte0Ncid1wg03"))
                .andExpect(jsonPath("$[0].total_price").value(142.54))
                .andExpect(jsonPath("$[0].currency").value("SGD"))
                .andExpect(jsonPath("$[0].items[0].quantity").value(5))
                .andExpect(jsonPath("$[0].items[0].name").value("Meiji Low Fat Chocolate Flavour Milk"))
                .andExpect(jsonPath("$[0].items[1].quantity").value(3))
                .andExpect(jsonPath("$[0].items[1].name").value("Vitasoy Soya Bean Drink"))
                .andExpect(jsonPath("$[0].items[2].quantity").value(1))
                .andExpect(jsonPath("$[0].items[2].name").value("Win2 Potato Crisp Vegetable Flavour"))
                .andExpect(jsonPath("$[0].tid").value("ZiKz4NgADn5g6IhBqILB"));
    }

    // test case 3: For getTransactionsByUEN, testing valid UEN and status = PENDING-ACCEPTANCE
    @Test
    public void getValidTransaction_ValidUEN_StatusPendingAcceptance() throws Exception {
        // Mocking QueryDocumentSnapshot to simulate Firebase data
        QueryDocumentSnapshot documentSnapshot = Mockito.mock(QueryDocumentSnapshot.class);

        // Define the behavior of the mocked document snapshot
        when(documentSnapshot.get("uid")).thenReturn("8i9PDIBnKaa2SJBqzkVlIKyruCp1");
        when(documentSnapshot.get("total_price")).thenReturn(99.03);
        when(documentSnapshot.contains("converted_price")).thenReturn(false);
        when(documentSnapshot.getId()).thenReturn("Z5wdGvwiUqbJoCM23OJB");

        // Create mock data for `items`
        ArrayList<Object> items = new ArrayList<>();
        items.add(Map.of("quantity", 3, "name", "SSH DARK SOYA SAUCE (BROWN)"));

        // Mock `getProductListfromTransaction` to return the `items` array
        when(wholesalerService.UENExists("200915506H")).thenReturn(true);
        when(wholesalerService.isValidStatus("PENDING-ACCEPTANCE")).thenReturn(true);
        when(transactionService.getProductListfromTransaction(documentSnapshot, false)).thenReturn(items);

        // Mock the transaction service to return a list containing this document snapshot
        List<QueryDocumentSnapshot> docList = Collections.singletonList(documentSnapshot);
        when(transactionService.getDocByUENAndStatus("200915506H", "PENDING-ACCEPTANCE")).thenReturn(docList);

        // Perform the GET request and validate the response structure
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                        .param("uen", "200915506H")
                        .param("status", "PENDING-ACCEPTANCE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uid").value("8i9PDIBnKaa2SJBqzkVlIKyruCp1"))
                .andExpect(jsonPath("$[0].total_price").value(99.03))
                .andExpect(jsonPath("$[0].currency").value("SGD"))
                .andExpect(jsonPath("$[0].items[0].quantity").value(3))
                .andExpect(jsonPath("$[0].items[0].name").value("SSH DARK SOYA SAUCE (BROWN)"))
                .andExpect(jsonPath("$[0].tid").value("Z5wdGvwiUqbJoCM23OJB"));
    }

    // test case 4: Invalid UEN (Wrong format)
    @Test
    public void getInvalidTransaction_UenWrongFormat() throws Exception {
        // Perform the get request with the wrong format for the UEN and check that it fails
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                .param("uen", "12345")
                .param("status", "IN-CART")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Test case 5: Invalid UEN (empty)
    @Test
    public void getTransaction_EmptyUen() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                .param("uen", "")
                .param("status", "IN-CART")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    // Test case 6: For getTransactionByUEN, status value is invalid (empty/missing)
    @Test
    public void getInvalidTransaction_StatusMissing() throws Exception {
        // Perform the GET request without a status parameter and check that it returns BAD_REQUEST
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                        .param("uen", "199203796C")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Test case 7: For getTransactionByUEN, status value is an unknown status
    @Test
    public void getInvalidTransaction_StatusUnknown() throws Exception {
        // Perform the GET request without a status parameter and check that it returns BAD_REQUEST
        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/get")
                        .param("uen", "199203796C")
                        .param("status", "completed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}