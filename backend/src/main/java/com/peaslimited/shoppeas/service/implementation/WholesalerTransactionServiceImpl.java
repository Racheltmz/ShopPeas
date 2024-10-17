package com.peaslimited.shoppeas.service.implementation;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peaslimited.shoppeas.dto.WholesalerTransactionsDTO;
import com.peaslimited.shoppeas.repository.WholesalerTransactionRepo;
import com.peaslimited.shoppeas.service.WholesalerTransactionsService;
import java.util.Map;

@Service
public class WholesalerTransactionServiceImpl implements WholesalerTransactionsService{

    @Autowired
    private WholesalerTransactionRepo wholesalerTransactionRepo;

    /* @Override
    public WholesalerTransactionsDTO getWTransactions(String tid) throws ExecutionException, InterruptedException
    {
        return 
    } */

    @Override
    public void addWTransaction(String tid, WholesalerTransactionsDTO wTransaction)
    {
        wholesalerTransactionRepo.addByTID(tid, wTransaction);
    }

    @Override
    // update transaction status
    public void updateWTransactionStatus(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException
    {

    }

}
