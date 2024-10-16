package com.peaslimited.shoppeas.repository.implementation;

import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.OrderDTO;
import com.peaslimited.shoppeas.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final String COLLECTION = "orders";

    @Autowired
    private Firestore firestore;

    @Override
    public void addByOID(String OID, OrderDTO order)
    {
        firestore.collection(COLLECTION).document(OID).set(order);
    }

}
