package com.peaslimited.shoppeas.repository;

import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;

public interface WholesalerTransactionRepo {

    void addByTID(String TID, WholesalerTransactionsDTO wTransaction);

}
