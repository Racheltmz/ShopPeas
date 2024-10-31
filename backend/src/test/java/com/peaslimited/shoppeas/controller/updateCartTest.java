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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private WholesalerProductCacheServiceImpl wholesalerProductCacheService;

    @MockBean
    private CartRepository cartRepository;

    /*@Test
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
    }*/

}