package com.peaslimited.shoppeas.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.TransactionsOrderedDTO;
import com.peaslimited.shoppeas.model.Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface TransactionsRepository {

    TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException;

    Transactions findCartTransaction(String uid, String uen) throws ExecutionException, InterruptedException;

    List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException;

    ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException;

    String addTransaction(TransactionsDTO transactionsDTO);

    void updateTransactionProduct(Transactions transaction, String uid, Map<String, Object> data);

    void updateProductQuantity(String uid, String uen, String swp_id, int quantity, double price) throws ExecutionException, InterruptedException;

    Map<String, Object> updateProductList(String uid, String uen, String swp_id) throws ExecutionException, InterruptedException;

    TransactionsOrderedDTO getHistoryDetails(String orderId) throws ExecutionException, InterruptedException;

    void updateTransactionRated(String tid);

    void updateTransactionStatus(Map<String, Object> data);


//
//    DocumentSnapshot getDocByUENAndWName(String uen, String uid) throws ExecutionException, InterruptedException;
//
//    void createTransaction(TransactionsDTO transactionsDTO);
//
//    void updateTransaction(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException;
  
}
