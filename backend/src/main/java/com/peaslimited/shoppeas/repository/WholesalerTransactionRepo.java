package com.peaslimited.shoppeas.repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;

public interface WholesalerTransactionRepo {

    //WholesalerTransactionsDTO findByTID() throws ExecutionException, InterruptedException;

    //WholesalerTransactionsDTO findByUID() throws ExecutionException, InterruptedException;

    //DocumentSnapshot findDocByTID(String TID) throws ExecutionException, InterruptedException;

    void addByTID(String TID, WholesalerTransactionsDTO wTransaction);

    //String updateStatusByTID(String TID, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
