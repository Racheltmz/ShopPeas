package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.model.Transactions;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.OrderHistoryService;
import com.peaslimited.shoppeas.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service

public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepo;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Override
    public TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException {
        return transactionsRepo.findByTID(tid);
    }

    @Override
    public List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException {
        return transactionsRepo.getDocByUENAndStatus(uen, status);
    }

    @Override
    public ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException {
        return transactionsRepo.getProductListfromTransaction(document, cart);
    }

    @Override
    public String updateTransactionProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        String uen = data.get("uen").toString();
        Transactions transaction = transactionsRepo.findCartTransaction(uid, uen);

        // ACTION: transaction record exists and product is from the same wholesaler
        if (transaction != null) {
            // update existing transaction record
            transactionsRepo.updateTransactionProduct(transaction, uid, data);
            return transaction.getTid();
        } else { // ACTION: transaction record exists and product is from different wholesaler or transaction record DNE
            // input: Single "item": swp_id, quantity, uen
            String swp_id = data.get("swp_id").toString();
            double price = Double.parseDouble(data.get("price").toString());
            int quantity = Integer.parseInt(data.get("quantity").toString());
            double total_price = Double.parseDouble(data.get("total_price").toString());
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("quantity", quantity);
            productMap.put("price", price);
            productMap.put("swp_id", swp_id);

            Map<String, Object> productsMapNew = new HashMap<>();
            productsMapNew.put(String.valueOf(0), productMap);

            return transactionsRepo.addTransaction(new TransactionsDTO(
                    productsMapNew,
                    false,
                    "IN-CART",
                    total_price,
                    uen,
                    uid
            ));
        }
    }

    @Override
    public void updateTransactionStatus(Map<String, Object> data) {
        transactionsRepo.updateTransactionStatus(data);
    }

    @Override
    public void updateToCheckout(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        //ACTION: GET TRANSACTION DATA
        //convert order data to array
        ArrayList<Object> cartList = (ArrayList<Object>) data.get("cart_items");

        // Check max number of orders in the cart
        if(cartList.size() > 10){
            throw new IllegalArgumentException("Too many orders in cart. Maximum allowed is 10");
        }

        // ACTION: add order history
        orderHistoryService.addOrderHistory(uid, cartList);
        System.out.println("Order history added successfully for UID: " + uid);

        // ACTION: delete cart
        String cid = cartRepository.findCIDByUID(uid);
        cartRepository.deleteCartOnCheckout(cid);
    }
}
