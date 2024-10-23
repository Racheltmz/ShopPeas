package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.TransactionsOrderedDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface TransactionsRepository {

    TransactionsDTO getTransactionByUID(String uid, String status) throws ExecutionException, InterruptedException;

    DocumentSnapshot findDocByUIDandStatus(String UID, String status) throws ExecutionException, InterruptedException;

    List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException;

    DocumentSnapshot getDocByUENAndWName(String uen, String uid) throws ExecutionException, InterruptedException;

    TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException;

    ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException;

    TransactionsOrderedDTO getHistoryDetails(String orderId) throws ExecutionException, InterruptedException;

    void createTransaction(TransactionsDTO transactionsDTO);

    void updateTransactionProduct(Map<String, Object> data, String uid, String status) throws ExecutionException, InterruptedException;

    void updateTransactionStatus(Map<String, Object> data);

    void updateTransactionRated(String tid);

    void updateTransaction(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException;
  
    List<QueryDocumentSnapshot> findDocListByUID(String UID) throws ExecutionException, InterruptedException;

}
