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
public class checkoutTests {

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

    // test case 1: Valid shopping cart with multiple orders
    @Test
    public void validCheckout_MultipleOrders() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Win2 Potato Crisp Vegetable Flavour",
                                    "quantity": 1
                                },
                                {
                                    "name":"Nestle MILO Calcium Plus (Activ-Go)",
                                    "quantity": 1
                                }
                            ],
                            "uen":"201936456R"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 3
                                },
                                {
                                    "name":"SSH CHILLI CRAB SAUCE PREMIUM",
                                    "quantity": 2
                                }
                            ],
                            "uen":"199203796C"
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isCreated());  // Expect a 201 Created status
    }
    // test case 2: valid order with 1 order
    @Test
    public void validCheckout_SingleOrders() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Win2 Potato Crisp Vegetable Flavour",
                                    "quantity": 1
                                },
                                {
                                    "name":"Nestle MILO Calcium Plus (Activ-Go)",
                                    "quantity": 1
                                }
                            ],
                            "uen":"201936456R"
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isCreated());  // Expect a 201 Created status
    }

    // test case 3: valid order with 4 orders
    @Test
    public void validCheckout_FourOrders() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Win2 Potato Crisp Vegetable Flavour",
                                    "quantity": 1
                                },
                                {
                                    "name":"Nestle MILO Calcium Plus (Activ-Go)",
                                    "quantity": 1
                                }
                            ],
                            "uen":"201936456R"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 3
                                },
                                {
                                    "name":"SSH CHILLI CRAB SAUCE PREMIUM",
                                    "quantity": 2
                                }
                            ],
                            "uen":"199203796C"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 3
                                }
                            ],
                            "uen":"199203796C"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 2
                                }
                            ],
                            "uen":"199203796C"
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isCreated());  // Expect a 201 Created status
    }

    // Case 4: Invalid test case of 5 orders
    @Test
    public void invalidCheckout_FiveOrders() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Win2 Potato Crisp Vegetable Flavour",
                                    "quantity": 1
                                },
                                {
                                    "name":"Nestle MILO Calcium Plus (Activ-Go)",
                                    "quantity": 1
                                }
                            ],
                            "uen":"201936456R"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 3
                                },
                                {
                                    "name":"SSH CHILLI CRAB SAUCE PREMIUM",
                                    "quantity": 2
                                }
                            ],
                            "uen":"199203796C"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 3
                                }
                            ],
                            "uen":"199203796C"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 2
                                }
                            ],
                            "uen":"199203796C"
                        },
                        {
                            "wholesaler": "Market's Best Pte Ltd",
                            "items": [
                                {
                                    "name":"Lim Traders Satay Chicken",
                                    "quantity": 2
                                }
                            ],
                            "uen":"199203796C"
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        when(wholesalerService.isValidWholesalerAndUEN("Market's Best Pte Ltd", "199203796C")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 bad request
    }

    // Test case 5: Invalid test case of 0 orders
    @Test
    public void invalidCheckout_NoOrders() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": []
                }
        """;


        // Mock service behavior
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 bad request
    }

    // Test case 6: Valid shopping cart with 1 order and 1 item
    @Test
    public void validCheckout_Singleitem() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
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
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isCreated());  // Expect a 201 Created status
    }

    // test case 7: Valid checkout with 1 order and 5 items
    @Test
    public void validCheckout_Fiveitem() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                }
                            ],
                            "uen":"201936456R"
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isCreated());  // Expect a 201 Created status
    }

    // test case 8: Invalid test case with 0 items
    @Test
    public void invalidCheckout_Noitem() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [],
                            "uen":"201936456R"
                        }
                    ]
                }
        """;

        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 bad request
    }

    // test case 9: invalid order with 6 items
    @Test
    public void invalidCheckout_Sixitem() throws Exception {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a json body to post for checkout
        String checkoutJson = """
                {
                    "cart_items": [
                        {
                            "wholesaler": "International Premium Food Traders Pte Ltd",
                            "items": [
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                },
                                {
                                    "name":"Borges Extra Virgin Olive Oil",
                                    "quantity": 1
                                }
                            ],
                            "uen":"201936456R"
                        }
                    ]
                }
        """;


        // Mock service behavior
        when(wholesalerService.isValidWholesalerAndUEN("International Premium Food Traders Pte Ltd", "201936456R")).thenReturn(true);
        doNothing().when(orderHistoryService).addOrderHistory(anyString(), any());
        when(cartRepository.findCIDByUID("uid")).thenReturn("test-cart-id");
        doNothing().when(cartRepository).deleteCartOnCheckout(anyString());


        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(checkoutJson))
                .andExpect(status().isBadRequest());  // Expect a 400 bad request
    }
}