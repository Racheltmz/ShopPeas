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

/**
 * Unit tests for the checkout functionality in Transaction Controller using black box testing
 * Tests cover various error cases including invalid orders, missing fields, and incorrect values.
 */
@WebMvcTest(value = TransactionController.class,  excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class checkoutTests2 {

    /**
     * Sets up the MockMvc for performing HTTP requests in the tests.
     */
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

    /**
     * Mocks the authentication process for tests.
     * 
     * @param userId the user ID to set in the authentication context
     */
    private void mockAuthentication(String userId) {
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(userId);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    /**
     * Invalid checkout with one valid and one invalid order (incorrect wholesaler).
     * Expects a 400 Bad Request status due to invalid wholesaler information.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout with an empty wholesaler field.
     * Expects a 400 Bad Request status due to the missing wholesaler name.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout where the items array contains null values.
     * Expects a 400 Bad Request status due to null values in the items array.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout with a missing field (e.g., UEN).
     * Expects a 400 Bad Request status due to the missing UEN field.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout with an incorrect wholesaler.
     * Expects a 400 Bad Request status due to an invalid wholesaler name.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout with an incorrect UEN.
     * Expects a 400 Bad Request status due to an invalid UEN.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout with a missing quantity field in items.
     * Expects a 400 Bad Request status due to missing quantity in the item details.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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

    /**
     * Invalid checkout with an item quantity of zero.
     * Expects a 400 Bad Request status due to an invalid quantity (zero) in the item details.
     * 
     * @throws Exception if the request fails unexpectedly
     */
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