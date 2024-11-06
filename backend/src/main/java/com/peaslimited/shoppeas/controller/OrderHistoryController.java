package com.peaslimited.shoppeas.controller;

import com.peaslimited.shoppeas.dto.OrderHistoryDTO;
import com.peaslimited.shoppeas.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class handles operations surrounding the user's order history, specifically retrieving order history data
 * from FireBase.
 */
@CrossOrigin
@RestController
@RequestMapping("/history")
public class OrderHistoryController {
    @Autowired
    private OrderHistoryService orderService;

    /**
     * Fetches the consumer's order history from FireBase, and is called from the frontend with HTTP path
     * "/history/view".
     * @return List<OrderHistoryDTO> of OrderHistoryDTO objects.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/view")
    @PreAuthorize("hasRole('CONSUMER')")
    @ResponseStatus(code = HttpStatus.OK)
    public List<OrderHistoryDTO> viewOrderHistory() throws ExecutionException, InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (String) authentication.getPrincipal();
        return orderService.getOrderHistory(uid);
    }

}
