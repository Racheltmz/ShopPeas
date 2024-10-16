package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepo;

    @Override
    public TransactionsDTO getTransactionByUID(String uid, String status) throws ExecutionException, InterruptedException
    {
        return transactionsRepo.getTransactionByUID(uid, status);
    }

    @Override
    public DocumentSnapshot findDocByUIDandStatus(String UID, String status) throws ExecutionException, InterruptedException
    {
        return transactionsRepo.findDocByUIDandStatus(UID, status);
    }

    @Override
    public List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException
    {
        return  transactionsRepo.getDocByUENAndStatus(uen, status);
    }

    @Override
    public DocumentSnapshot getDocByUENAndWName(String uen, String uid) throws ExecutionException, InterruptedException
    {
        return transactionsRepo.getDocByUENAndWName(uen, uid);
    }

    @Override
    public ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException
    {
        return transactionsRepo.getProductListfromTransaction(document, cart);
    }

    @Override
    public void createTransaction(TransactionsDTO transactionsDTO)
    {
        transactionsRepo.createTransaction(transactionsDTO);
    }

    @Override
    public void updateTransactionProduct(Map<String, Object> data, String uid, String status)throws ExecutionException, InterruptedException
    {
        transactionsRepo.updateTransactionProduct(data, uid, status);
    }

    @Override
    public void updateTransactionStatus(Map<String, Object> data)
    {
        transactionsRepo.updateTransactionStatus(data);
    }

    @Override
    public List<QueryDocumentSnapshot> findDocListByUID(String UID) throws ExecutionException, InterruptedException
    {
        return transactionsRepo.findDocListByUID(UID);
    }

}
