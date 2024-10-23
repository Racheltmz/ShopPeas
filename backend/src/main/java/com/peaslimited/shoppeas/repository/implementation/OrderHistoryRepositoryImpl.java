package com.peaslimited.shoppeas.repository.implementation;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.repository.OrderHistoryRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final String COLLECTION = "order_history";

    @Autowired
    private Firestore firestore;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private WholesalerRepository wholesalerRepository;

    @Autowired
    private WholesalerProductRepository wholesalerProductRepository;

    @Override
    public List<OrderHistoryDTO> getOrderHistoryByUID(String uid) throws ExecutionException, InterruptedException {
        QuerySnapshot snapshot = firestore.collection(COLLECTION).whereEqualTo("uid", uid).get().get();

        return snapshot.getDocuments().stream()
            .map(doc -> {
                Map<String, Object> data = doc.getData();
                data.put("oid", doc.getId());
                Timestamp timestamp = doc.get("date", Timestamp.class);
                LocalDate orderDate = null;
                if (timestamp != null) {
                    orderDate = LocalDate.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault());
                }

                ArrayList<OrderWholesalerItemsDTO> orderWholesalerItems = new ArrayList<>();
                List<String> orderIDs = (List<String>) doc.get("orders");

                assert orderIDs != null;
                for (String orderId : orderIDs) {
                    try {
                        TransactionsDTO transactionInfo = transactionsRepository.getHistoryDetails(orderId);

                        String uen = transactionInfo.getUen();

                        ArrayList<Object> products = transactionInfo.getProducts();

                        String status = transactionInfo.getStatus();
                        double total_price = transactionInfo.getTotal_price();

                        ArrayList<OrderItemDTO> orderItemDTOs = new ArrayList<>();
                        // Get wholesaler name
                        String wholesalerName = wholesalerRepository.findWholesalerName(uen);

                        // Get product information
                        for (Object product: products) {
                            Map<String, Object> productsMap = (Map<String, Object>) product;

                            String swp_id = String.valueOf(productsMap.get("swp_id"));
                            int quantity = Integer.parseInt(String.valueOf(productsMap.get("quantity")));
                            double price = Double.parseDouble(String.valueOf(productsMap.get("price")));
                            // Get product name and description from swp_id
                            String name = wholesalerProductRepository.getWholesalerProductName(swp_id);
                            String desc = wholesalerProductRepository.getWholesalerProductDesc(swp_id);
                            String image = wholesalerProductRepository.getWholesalerProductImg(swp_id);
                            orderItemDTOs.add(new OrderItemDTO(name, desc, image, quantity, price));
                        }
                        OrderWholesalerItemsDTO item = new OrderWholesalerItemsDTO(wholesalerName, orderItemDTOs, status, total_price);
                        orderWholesalerItems.add(item);
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return new OrderHistoryDTO(
                    data.get("oid").toString(),
                    orderDate,
                    orderWholesalerItems
                );
            })
            .collect(Collectors.toList());
    }
}
