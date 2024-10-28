package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.repository.OrderHistoryRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final String COLLECTION = "order_history";

    @Autowired
    private Firestore firestore;

    @Autowired
    private CurrencyService currencyService;

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
                        TransactionsOrderedDTO transactionInfo = transactionsRepository.getHistoryDetails(orderId);

                        String uen = transactionInfo.getUen();

                        ArrayList<Object> products = transactionInfo.getProducts();

                        String status = transactionInfo.getStatus();
                        double total_price = transactionInfo.getTotal_price();
                        boolean rated = transactionInfo.isRated();

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
                        OrderWholesalerItemsDTO item = new OrderWholesalerItemsDTO(orderId, uen, wholesalerName, orderItemDTOs, status, total_price, rated);
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

    @Override
    public void addOrderHistory(String uid, ArrayList<String> uenList, ArrayList<String> transactionList, ArrayList<Double> priceList) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        // Get the current date in desired format
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Set<String> uniqueWholesalers = new HashSet<>(uenList);

        // Loop through each unique UEN to group transactions
        for (String uen : uniqueWholesalers) {

            // Find transactions for the current UEN
            List<String> uenTransactions = new ArrayList<>();
            double uenTotalPrice = 0.0;

            for (int i = 0; i < uenList.size(); i++) {
                if (uenList.get(i).equals(uen)) {
                    uenTransactions.add(transactionList.get(i));
                    uenTotalPrice += priceList.get(i);
                }
            }

            // Get wholesaler's preferred currency
            String preferredCurrency = wholesalerRepository.findByUEN(uen).getCurrency();

            // Update total price if currency is MYR
            if (preferredCurrency.equals("MYR")) {
                double exchangeRate = currencyService.exchangeRate(uenTotalPrice, preferredCurrency);
                uenTotalPrice = uenTotalPrice * exchangeRate;
            }

            // Add document data with auto-generated ID
            Map<String, Object> data = new HashMap<>();
            data.put("uid", uid);
            data.put("date", currentDate);
            data.put("orders", uenTransactions);
            data.put("total_price", uenTotalPrice);

            firestore.collection(COLLECTION).add(data);
        }
    }
}
