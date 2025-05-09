package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * This class tests the updateCart() function in CartController using Basis Path Testing.
 */
@WebMvcTest( value = CartController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class updateCartTest {

    @Autowired
    private MockMvc mockMvc;

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
    private TransactionsRepository transactionsRepository;


    @MockBean
    private WholesalerProductCacheServiceImpl wholesalerProductCacheService;

    @MockBean
    private CartRepository cartRepository;

    /**
     * Test case 2.2.a: Tests the updateCart() function in CartController with a valid JSON input.
     * @throws Exception
     */
    @Test
    public void getValid_updateCart() throws Exception {
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        String swp_id = "1QguhYaLLF1GcWiiCCPR";
        String uen = "200915506H";
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
                + "\"total_price\": 454.9"
                + "}";

        // ACTION: check that wholesaler exists
        when(wholesalerService.UENExists(uen)).thenReturn(true);
        // ACTION: check that the wholesaler product exists
        WholesalerProductDTO product = new WholesalerProductDTO();
        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(product);
        // ACTION: check that the cart exists
        String cid = "";
        when(cartRepository.findCIDByUID(uid)).thenReturn(cid);

        Mockito.doNothing().when(transactionsRepository).updateProductQuantity(uid, uen, swp_id,quantity,total_price);
        Mockito.doNothing().when(cartRepository).updateCartPrice(Mockito.anyString(), Mockito.anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.patch("/cart/update")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isNoContent());
    }

    /**
     * Test case 2.2.b: Tests the updateCart() function in CartController with an invalid JSON input (invalid uen,
     * wholesaler does not exist).
     * @throws Exception
     */
    @Test
    public void wholesalerDNE_updateCart() throws Exception {
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        String swp_id = "1QguhYaLLF1GcWiiCCPR";
        String uen = "123";
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
                + "\"uen\": \"123\", "
                + "\"quantity\": 10, "
                + "\"total_price\": 454.9"
                + "}";

        // ACTION: check that wholesaler exists
        when(wholesalerService.UENExists(uen)).thenReturn(false);
        // ACTION: check that the wholesaler product exists
        WholesalerProductDTO product = new WholesalerProductDTO();
        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(product);
        // ACTION: check that the cart exists
        String cid = "";
        when(cartRepository.findCIDByUID(uid)).thenReturn(cid);

        Mockito.doNothing().when(transactionsRepository).updateProductQuantity(uid, uen, swp_id,quantity,total_price);
        Mockito.doNothing().when(cartRepository).updateCartPrice(Mockito.anyString(), Mockito.anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.patch("/cart/update")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isBadRequest());
    }

    /**
     * Test case 2.2.c: Tests the updateCart() function in CartController with an invalid JSON input (invalid swp_id,
     * wholesaler product does not exist).
     * @throws Exception
     */
    @Test
    public void productDNE_updateCart() throws Exception {
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        String swp_id = "123";
        String uen = "200915506H";
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
                + "\"swp_id\": \"123\", "
                + "\"uen\": \"200915506H\", "
                + "\"quantity\": 10, "
                + "\"total_price\": 454.9"
                + "}";

        // ACTION: check that wholesaler exists
        when(wholesalerService.UENExists(uen)).thenReturn(true);
        // ACTION: check that the wholesaler product exists
        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(null);
        // ACTION: check that the cart exists
        String cid = "";
        when(cartRepository.findCIDByUID(uid)).thenReturn(cid);

        Mockito.doNothing().when(transactionsRepository).updateProductQuantity(uid, uen, swp_id,quantity,total_price);
        Mockito.doNothing().when(cartRepository).updateCartPrice(Mockito.anyString(), Mockito.anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.patch("/cart/update")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isBadRequest());
    }

    /**
     * Test case 2.2.d: Tests the updateCart() function in CartController where the cart record does not exist for the
     * user
     * @throws Exception
     */
    @Test
    public void cartDNE_updateCart() throws Exception {
        String uid = "8i9PDIBnKaa2SJBqzkVlIKyruCp1";
        String swp_id = "1QguhYaLLF1GcWiiCCPR";
        String uen = "200915506H";
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
                + "\"total_price\": 454.9"
                + "}";

        // ACTION: check that wholesaler exists
        when(wholesalerService.UENExists(uen)).thenReturn(true);
        // ACTION: check that the wholesaler product exists
        when(wholesalerProductService.getBySwp_id(swp_id)).thenReturn(new WholesalerProductDTO());
        // ACTION: check that the cart exists
        when(cartRepository.findCIDByUID(uid)).thenReturn(null);

        Mockito.doNothing().when(transactionsRepository).updateProductQuantity(uid, uen, swp_id,quantity,total_price);
        Mockito.doNothing().when(cartRepository).updateCartPrice(Mockito.anyString(), Mockito.anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.patch("/cart/update")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isBadRequest());
    }

}