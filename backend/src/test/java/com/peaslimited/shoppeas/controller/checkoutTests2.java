package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.service.TransactionsService;
import com.peaslimited.shoppeas.service.OrderHistoryService;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.service.WholesalerService;
import com.peaslimited.shoppeas.service.implementation.TransactionCacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TransactionController.class,  excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class checkoutTests2 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionsService transactionsService;

    @MockBean
    private TransactionCacheServiceImpl transactionCacheService;
    @MockBean
    private OrderHistoryService orderHistoryService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private Authentication authentication;

    @MockBean
    private WholesalerService wholesalerService;

    // helper method for authentication
    private void mockAuthentication(String userId) {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userId);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    // test case 10: 1 valid and 1 invalid order
    @Test
    public void invalidCheckout_ValidAndInvalidOrder() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        // Create a JSON body for checkout with one valid order and one invalid order (incorrect wholesaler)
        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "International Premium Food Traders Pte Ltd",
                        "items": [
                            {
                                "name":"Borges Extra Virgin Olive Oil",
                                "quantity": 1
                            }
                        ],
                        "uen":"201936456R"
                    },
                    {
                        "wholesaler": "Burger King",
                        "items": [
                            {
                                "name":"SSH CHILLI CRAB SAUCE PREMIUM",
                                "quantity": 2
                            }
                        ],
                        "uen":"BK1001"
                    }
                ]
            }
    """;

        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Burger King", "BK1001")).thenReturn(false);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request
    }

    // test case 11: invalid test case with an empty string for the wholesaler field
    @Test
    public void invalidCheckout_EmptyWholesalerField() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "",
                        "items": [
                            {
                                "name": "Borges Extra Virgin Olive Oil",
                                "quantity": 1
                            }
                        ],
                        "uen": "201936456R"
                    }
                ]
            }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    // Test case 12: Invalid shopping cart where items contain null values
    @Test
    public void invalidCheckout_NullValuesInItemsArray() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "International Premium Food Traders Pte Ltd",
                        "items": [null],
                        "uen": "201936456R"
                    }
                ]
            }
    """;

              mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    // test case 13: Invalid cart with a missing field such as uen or wholesaler
    @Test
    public void invalidCheckout_MissingUenField() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "International Premium Food Traders Pte Ltd",
                        "items": [
                            {
                                "name": "Borges Extra Virgin Olive Oil",
                                "quantity": 1
                            }
                        ]
                    }
                ]
            }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    // Test case 14: Invalid wholesaler
    @Test
    public void invalidWholesaler() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "Mos Burger",
                        "items": {
                                "name": "Borges Extra Virgin Olive Oil",
                                "quantity": 1
                            },
                        "uen": "201936456R"
                    }
                ]
            }
    """;
        when(wholesalerService.isValidWholesalerAndUEN("Mos Burger", "201936456R")).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    // test case 15: Invalid UEN
    @Test
    public void invalidUEN() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "International Premium Food Traders Pte Ltd",
                        "items": {
                                "name": "Borges Extra Virgin Olive Oil",
                                "quantity": 1
                            },
                        "uen": "12345"
                    }
                ]
            }
    """;
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "12345")).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    // test case 16: missing quantity in items
    @Test
    public void missingQuantity() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);

        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "International Premium Food Traders Pte Ltd",
                        "items": {
                                "name": "Borges Extra Virgin Olive Oil",
                            },
                        "uen": "12345"
                    }
                ]
            }
    """;

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request status
    }

    // test case 17: Invalid shopping cart with quantity 0
    @Test
    public void invalidCheckout_ZeroQuantity() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        mockAuthentication(uid);
        // Create a JSON body to post for checkout, with one item having quantity 0
        String checkoutJson = """
            {
                "cart_items": [
                    {
                        "wholesaler": "International Premium Food Traders Pte Ltd",
                        "items": [
                            {
                                "name":"Borges Extra Virgin Olive Oil",
                                "quantity": 0
                            }
                        ],
                        "uen":"201936456R"
                    }
                ]
            }
    """;

        // Mock service behavior
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());

        // Perform the request and expect a 400 BAD REQUEST status
        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request
    }


}