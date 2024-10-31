package com.peaslimited.shoppeas.repository.implementation;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.model.Transactions;
import com.peaslimited.shoppeas.repository.OrderHistoryRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.repository.WholesalerProductRepository;
import com.peaslimited.shoppeas.repository.WholesalerRepository;
import com.peaslimited.shoppeas.service.CurrencyService;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
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

    @Autowired
    private WholesalerProductService wholesalerProductService;

    /**
     * Calls updateWProductQuant() to update the product stock for each item in each transaction checked out by
     * the consumer.
     * @param productsListMap The map of products in one transaction record
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void updateStockAfterCheckout(Map<String, Object> productsListMap) throws ExecutionException, InterruptedException {

        //get each product in one transaction record
        for(int i = 0; i < productsListMap.size(); i++)
        {
            Map<String, Object> orderMap = (Map<String, Object>) productsListMap.get(String.valueOf(i));
            String swpid = orderMap.get("swp_id").toString();
            int quantity = Integer.parseInt(orderMap.get("quantity").toString());

            //update wholesaler product "quantity" field for respective product
            updateWProductQuant(swpid,quantity);
        }
    }

    /**
     * Updates the stock quantity in a wholesaler product record by subtracting the amount ordered by a consumer from the original
     * stock quantity.
     * @param swp_id The wholesaler product ID of the product ordered by a consumer.
     * @param quantity The quantity of a product ordered by a consumer.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void updateWProductQuant(String swp_id, int quantity) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProductDTO = wholesalerProductService.getBySwp_id(swp_id);
        float oldQuantity = wholesalerProductDTO.getStock();

        Map<String, Object> wholeSalerProductMap = new HashMap<>();
        wholeSalerProductMap.put("stock", oldQuantity - quantity);

        wholesalerProductService.updateWholesalerProduct(swp_id, wholeSalerProductMap);
    }

    /**
     * Gets a list of the consumer's order history.
     * @param uid Consumer's user ID.
     * @return A list of OrderHistoryDTO objects.
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * Adds an order history record based on the items checked out from the consumer's shopping cart, updates the stock quantity in
     * wholesaler product records, updates the transaction status from IN-CART to PENDING-ACCEPTANCE, and updates the transaction price
     * for the transaction (i.e., currency conversion).
     * @param uid Consumer's user ID.
     * @param cartList List of contents of the cart (for each transaction in the cart: wholesaler name, list of items
     *                 (containing item name and quantity) and the wholesaler uen.
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     */
    @Override
    public void addOrderHistory(String uid, ArrayList<Object> cartList) throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        // ACTION: get transaction IDs (tid)
        ArrayList<String> transactionList = new ArrayList<>();
        double checkoutPrice = 0;

        Date date = new Date();
        Timestamp currentDate = Timestamp.of(date);

        for (Object cart: cartList) {
            Map<String, Object> cartMap = (Map<String, Object>) cart;

            String uen = (String) cartMap.get("uen");
            Transactions transaction = transactionsRepository.findCartTransaction(uid, uen);

            // update stock quantity in wholesaler product records
            Map<String, Object> productsListMap = transaction.getProducts();
            updateStockAfterCheckout(productsListMap);

            // Get tid
            String tid = transaction.getTid();
            transactionList.add(tid);

            // Get price
            double price = transaction.getTotal_price();
            checkoutPrice += price;

            // Update transaction status
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("tid", tid);
            statusMap.put("status", "PENDING-ACCEPTANCE");
            transactionsRepository.updateTransactionStatus(statusMap);

            String preferred_currency = wholesalerRepository.findByUEN(uen).getCurrency();
            if (Objects.equals(preferred_currency, "MYR")) {
                price = currencyService.exchangeRate(price, "MYR");
            }
            transactionsRepository.updateTransactionPrice(tid, price);
        }

        OrderHistoryByUserDTO order_history = new OrderHistoryByUserDTO(currentDate, transactionList, checkoutPrice, uid);
        firestore.collection(COLLECTION).add(order_history);
    }
}


