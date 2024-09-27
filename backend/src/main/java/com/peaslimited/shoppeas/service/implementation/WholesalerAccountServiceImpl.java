package com.peaslimited.shoppeas.service.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.peaslimited.shoppeas.model.WholesalerAccount;
import com.peaslimited.shoppeas.service.WholesalerAccountService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WholesalerAccountServiceImpl implements WholesalerAccountService {

    private final Firestore firestore;

    public WholesalerAccountServiceImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public WholesalerAccount getWholesalerAccount(String UEN) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("wholesaler_account").document(UEN);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        // Convert document to Wholesaler object
        WholesalerAccount wholesalerAccount = null;
        if (document.exists()) {
            wholesalerAccount = document.toObject(WholesalerAccount.class);
        }
        return wholesalerAccount;
    }

    @Override
    public void addWholesalerAccount(String UEN, WholesalerAccount wholesalerAccount) {
        // Create a Map to represent the data
        Map<String, Object> data = new HashMap<>();
        data.put("bank", wholesalerAccount.getBank());
        data.put("bank_account_name", wholesalerAccount.getBank_account_name());
        data.put("bank_account_no", wholesalerAccount.getBank_account_no());
        ApiFuture<WriteResult> future = firestore.collection("wholesaler_account").document(UEN).set(data);
    }
}