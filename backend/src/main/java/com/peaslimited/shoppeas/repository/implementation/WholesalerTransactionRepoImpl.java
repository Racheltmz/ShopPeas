package com.peaslimited.shoppeas.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;
import com.peaslimited.shoppeas.repository.WholesalerTransactionRepo;

// TODO: WAT IS THIS FOR
@Repository
public class WholesalerTransactionRepoImpl implements WholesalerTransactionRepo{

    private final String COLLECTION = "transactions";

    @Autowired
    private Firestore firestore;

    @Override
    public void addByTID(String TID, WholesalerTransactionsDTO wTransaction)
    {
        firestore.collection(COLLECTION).document(TID).set(wTransaction);
    }

}
