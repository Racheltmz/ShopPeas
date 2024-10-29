package com.peaslimited.shoppeas.service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface TransactionsService {

    TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException;

//    String createTransaction(String swp_id, int quantity, String uid, String uen) throws ExecutionException, InterruptedException;

    List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException;

    ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException;

    String updateTransactionProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException;

    void updateTransactionStatus(Map<String, Object> data);

    void updateToCheckout(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException;

//    double getProductPrice(String swp_id) throws ExecutionException, InterruptedException;



//
//    TransactionsDTO getTransactionByUID(String uid, String status) throws ExecutionException, InterruptedException;
//
//    DocumentSnapshot findDocByUIDandStatus(String uid, String status) throws ExecutionException, InterruptedException;
//
//
//
//    DocumentSnapshot getDocByUENAndWName(String uen, String uid) throws ExecutionException, InterruptedException;
//
//
//

//    void createTransaction(TransactionsDTO transactionsDTO);
//
//    void updateTransactionProduct(Map<String, Object> data, String uid, String status)throws ExecutionException, InterruptedException;
//

//    void updateTransaction(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException;

}
