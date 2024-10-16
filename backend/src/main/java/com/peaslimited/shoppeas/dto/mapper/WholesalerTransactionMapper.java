package com.peaslimited.shoppeas.dto.mapper;

import java.util.ArrayList;

import java.time.LocalDateTime;

import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;
import com.peaslimited.shoppeas.model.WholesalerTransactions;


public class WholesalerTransactionMapper {

    public static WholesalerTransactionsDTO toWTransactionDTO(WholesalerTransactions wTransaction)
    {
        String uid = wTransaction.getUid();
        ArrayList<String> orders = wTransaction.getOrders();
        double total_price = wTransaction.getTotal_price();
        String date = wTransaction.getDate();
        String status = wTransaction.getStatus();
        return new WholesalerTransactionsDTO(uid, orders, total_price, date, status);
    }

    public static WholesalerTransactionsDTO toWTransactionDTO(String uid, ArrayList<String> orders, double total_price, String date, String status)
    {
        return new WholesalerTransactionsDTO(uid, orders, total_price, date, status);
    }
}
