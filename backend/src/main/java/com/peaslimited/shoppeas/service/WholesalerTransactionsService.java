package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface WholesalerTransactionsService {

    //WholesalerTransactionsDTO getWTransactions(String tid) throws ExecutionException, InterruptedException;

    void addWTransaction(String tid, WholesalerTransactionsDTO wTransaction);

    // update transaction status
    void updateWTransactionStatus(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException;
}
