package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.ShoppingCartDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.service.*;
import com.peaslimited.shoppeas.service.implementation.WholesalerProductCacheServiceImpl;

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

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class tests the addToCart() function from CartController using Basis Path Testing.
 */
@WebMvcTest( value = CartController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class addToCartTest {
    @Autowired
    private MockMvc mockMvc;

    // Mock all dependencies of TransactionController
    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private WholesalerService wholesalerService;

    @MockBean
    private TransactionsService transactionService;

    @MockBean
    private WholesalerProductService wholesalerProductService;

    @MockBean
    private CartService cartService;


    @MockBean
    private WholesalerProductCacheServiceImpl wholesalerProductCacheService;

    @MockBean
    private CartRepository cartRepository;


    /**
     * Test case 2.1.a: Tests the addToCart() function in CartController with the default correct JSON input
     * and cart record does not exist for the user
     * @throws Exception
     */
    @Test
    public void getValid_addToCartTest_CartDNE() throws Exception {
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        String tid = "x5Po7bGDV85ROD1YuQBS";
        String swp_id = "1QguhYaLLF1GcWiiCCPR";
        int quantity = 10;
        double total_price = 454.9;

        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // ACTION: Create a JSON body to POST

        String requestBody = "{"
                + "\"swp_id\": \"1QguhYaLLF1GcWiiCCPR\", "
                + "\"uen\": \"200915506H\", "
                + "\"quantity\": 10, "
                + "\"total_price\": 454.9, "
                + "\"price\": 45.49"
                + "}";
        // ACTION: check that quantity is valid (quantity >= 0)
        when(cartService.checkQuantity(quantity)).thenReturn(true);
        // ACTION: check that the wholesaler product exists
        Mockito.when(wholesalerProductCacheService.doesTransactionExist(swp_id)).thenReturn(true);

        WholesalerProductDTO product = new WholesalerProductDTO();
        ArrayList<String> orders = new ArrayList<>();
        orders.add(tid);
        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(product);

        // ACTION: Update transaction record
        when(transactionService.updateTransactionProduct(Mockito.anyString(), Mockito.anyMap())).thenReturn(tid);
        // ACTION: Cart record does not exist for the user -> create a new cart record
        when(cartRepository.findCIDByUID(uid)).thenReturn(null);

        Mockito.doNothing().when(cartRepository).addCart(new ShoppingCartDTO(orders, uid, total_price));
        Mockito.doNothing().when(cartService).addCartItem(Mockito.anyString(), Mockito.anyMap());
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isCreated());
    }

    /**
     * Test case 2.1.b: Tests the addToCart() function in CartController with the default correct JSON input
     * and cart already exists for the user.
     * @throws Exception
     */
    @Test
    public void getValid_addToCartTest_CartExists() throws Exception
    {
        String uid = "hnByvuE2t0fviOCA0q7T8nsqZVp1";
        String tid = "x5Po7bGDV85ROD1YuQBS";
        String cid = "BHmwJoZLVh20s9PTjUwX";
        String swp_id = "1QguhYaLLF1GcWiiCCPR";
        int quantity = 10;
        double total_price = 454.9;

        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // ACTION: Create a JSON body to POST
        String requestBody = "{"
                + "\"swp_id\": \"1QguhYaLLF1GcWiiCCPR\", "
                + "\"uen\": \"200915506H\", "
                + "\"quantity\": 10, "
                + "\"total_price\": 454.9, "
                + "\"price\": 45.49"
                + "}";

        // ACTION: check that quantity is valid (quantity >= 0)
        when(cartService.checkQuantity(quantity)).thenReturn(true);
        // ACTION: check that the wholesaler product exists
        Mockito.when(wholesalerProductCacheService.doesTransactionExist(swp_id)).thenReturn(true);

        WholesalerProductDTO product = new WholesalerProductDTO();
        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(product);

        // ACTION: Update transaction record
        when(transactionService.updateTransactionProduct(Mockito.anyString(), Mockito.anyMap())).thenReturn(tid);
        // ACTION: Cart record exists for the user -> update cart record
        when(cartRepository.findCIDByUID(uid)).thenReturn(cid);

        Mockito.doNothing().when(cartRepository).updateCartWithOrder(cid, tid, quantity, total_price);
        Mockito.doNothing().when(cartService).addCartItem(Mockito.anyString(), Mockito.anyMap());

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }
    /**
     * Test case 2.1.c: Tests the addToCart() function in CartController with an invalid JSON input
     * (quantity of item <= 0)
     * @throws Exception
     */
    @Test
    public void addToCart_InvalidSwpid() throws Exception {
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        String tid = "x5Po7bGDV85ROD1YuQBS";
        String swp_id = "1234";
        String uen = "200915506H";
        int quantity = 10;
        double total_price = 454.9;
        double unit_price = 45.49;

        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a JSON body to patch
        String requestBody = "{"
                + "\"swp_id\": \"1234\", "
                + "\"uen\": \"200915506H\", "
                + "\"quantity\": 10, "
                + "\"total_price\": 454.9, "
                + "\"unit_price\": 45.49"
                + "}";

        Map<String, Object> data = Map.of(
                "swp_id", swp_id,
                "uen", uen,
                "quantity", quantity,
                "total_price", total_price,
                "price", unit_price);

        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(null); // Return null for invalid swp_id
        when(wholesalerProductCacheService.doesTransactionExist(swp_id)).thenReturn(false); // Return false for existence check

        //DOES NOT WORK
        Mockito.doNothing().when(cartService).addCartItem(uid, data);
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }


    /**
     * Test case 2.1.d: Tests the addToCart() function in CartController with an invalid JSON input
     * (invalid swp_id (wholesaler product does not exist)).
     * @throws Exception
     */
    @Test()
    public void addToCart_InvalidQuantity() throws Exception {
        int quantity = -10;
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        // ACTION: Mock authentication
        SecurityContext context = Mockito.mock(SecurityContext.class);
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(uid);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        // Create a JSON body to post
        String requestBody = "{"
                + "\"swp_id\": \"1QguhYaLLF1GcWiiCCPR\", "
                + "\"uen\": \"200915506H\", "
                + "\"quantity\": -10, "
                + "\"total_price\": 454.9, "
                + "\"unit_price\": 45.49"
                + "}";

        // Mock the service
        // ACTION: check that quantity is valid (quantity >= 0)
        when(cartService.checkQuantity(quantity)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }
}