package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.TransactionsOrderedDTO;
import com.peaslimited.shoppeas.model.Transactions;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class TransactionsRepositoryImpl implements TransactionsRepository {

    private final String COLLECTION = "transactions";

    @Autowired
    private Firestore firestore;

    @Autowired
    private WholesalerProductService wholesalerProductService;

    @Override
    public TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        // Convert document to Consumer object
        TransactionsDTO transactionsDTO = new TransactionsDTO();
        if (document.exists()) {
            transactionsDTO.setStatus(Objects.requireNonNull(document.get("status")).toString());
            transactionsDTO.setUen(Objects.requireNonNull(document.get("uen")).toString());
            transactionsDTO.setUid(Objects.requireNonNull(document.get("uid")).toString());
            transactionsDTO.setTotal_price(Float.parseFloat(Objects.requireNonNull(document.get("total_price")).toString()));

            Map<String, Object> productMap = (Map<String, Object>) document.get("products");
            Map<String, Object> products = new HashMap<>();
            for (int i = 0; i < Objects.requireNonNull(productMap).size(); i++) {
                String key = String.valueOf(i);
                Map<String, Object> newProduct = (Map<String, Object>) productMap.get(key);
                String index = Integer.toString(i);
                products.put(index, newProduct);
            }
            transactionsDTO.setProducts(products);

            return transactionsDTO;
        } else
            return null;
    }

    @Override
    public Transactions findCartTransaction(String uid, String uen) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION)
                .whereEqualTo("uid", uid)
                .whereEqualTo("uen", uen)
                .whereEqualTo("status", "IN-CART")
                .get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        DocumentSnapshot document = null;
        if (!documents.isEmpty()) {
            document = documents.getFirst();
            double total_price = Double.parseDouble(Objects.requireNonNull(document.get("total_price")).toString());
            Map<String, Object> products = (Map<String,Object>) document.get("products");
            // boolean rated = Boolean.parseBoolean(Objects.requireNonNull(document.get("rated")).toString());
            // Safely check if "rated" exists and set it to false if missing
            boolean rated = document.contains("rated") && Boolean.parseBoolean(document.get("rated").toString());
            String status = Objects.requireNonNull(document.get("status")).toString();

            return new Transactions(
                    document.getId(),
                    products,
                    rated,
                    status,
                    total_price,
                    uen,
                    uid
            );
        }
        return null;
    }

    @Override
    public List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uen", uen)
                .whereEqualTo("status", status).get();

        QuerySnapshot querySnapshot = query.get();

        return querySnapshot.getDocuments();
    }

    @Override
    public ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException {
        Map<String,Object> productList = (Map<String,Object>) document.get("products");
        ArrayList<Object> returnProdList = new ArrayList<>();

        for (int i = 0; i< productList.size(); i++) {
            String index = Integer.toString(i);
            Map<String, Object> docProduct = (Map<String, Object>) productList.get(index);
            Map<String, Object> product = new HashMap<>();
            String swpid = docProduct.get("swp_id").toString();
            int quantity = Integer.parseInt(docProduct.get("quantity").toString());

            String productName = wholesalerProductService.getWholesalerProductName(swpid);
            product.put("name", productName);
            product.put("quantity", quantity);

            returnProdList.add(product);
        }
        return returnProdList;
    }

    @Override
    public String addTransaction(TransactionsDTO transactionsDTO) {
        DocumentReference docRef = firestore.collection(COLLECTION).document();
        String tid = docRef.getId();
        docRef.set(transactionsDTO);
        return tid;
    }

    @Override
    public void updateTransactionProduct(Transactions transaction, String uid, Map<String, Object> data) {
        String swp_id = data.get("swp_id").toString();
        double price = Double.parseDouble(data.get("price").toString());
        int quantity = Integer.parseInt(data.get("quantity").toString());
        double total_price = Double.parseDouble(data.get("total_price").toString());
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("quantity", quantity);
        productMap.put("price", price);
        productMap.put("swp_id", swp_id);

        // Get current price
        double curTotalPrice = transaction.getTotal_price();

        // Get current products
        Map<String, Object> curProductsMap = transaction.getProducts();
        curProductsMap.put(String.valueOf(curProductsMap.size()), productMap);

        DocumentReference docRef = firestore.collection(COLLECTION).document(transaction.getTid());
        docRef.update("total_price", Double.parseDouble(String.format("%.2f", curTotalPrice + total_price)));
        docRef.update("products", curProductsMap);
    }

    @Override
    public void updateProductQuantity(String uid, String uen, String swp_id, int newQuantity, double price) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = firestore.collection(COLLECTION)
                .whereEqualTo("uen", uen)
                .whereEqualTo("uid", uid)
                .whereEqualTo("status", "IN-CART")
                .get().get();

        // Get document
        List<QueryDocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
        System.out.println(documentSnapshots);
        QueryDocumentSnapshot document = querySnapshot.getDocuments().getFirst();

        // Retrieve the products list
        Map<String, Object> products = document.toObject(Transactions.class).getProducts();

        for (Map.Entry<String, Object> entry : products.entrySet()) {
            Map<String, Object> productDetails = (Map<String, Object>) entry.getValue();
            System.out.println(productDetails);
            String record_swp_id = productDetails.get("swp_id").toString();

            // Update quantity in map
            if (record_swp_id.equals(swp_id)) {
                long quant = (long) productDetails.get("quantity");
                int oldQuant = Math.toIntExact(quant);
                System.out.print(newQuantity+oldQuant);
                productDetails.put("quantity", newQuantity+oldQuant);
                System.out.println(productDetails);
                // Update record's price
                document.getReference().update("total_price", Double.parseDouble(String.format("%.2f", document.toObject(Transactions.class).getTotal_price() + price)));
            }

        }
        System.out.println(products);
        // Update record's quantity and price
        document.getReference().update("products", products);

    }

    @Override
    public Map<String, Object> updateProductList(String uid, String uen, String swp_id) throws ExecutionException, InterruptedException {
        QuerySnapshot querySnapshot = firestore.collection(COLLECTION)
                .whereEqualTo("uen", uen)
                .whereEqualTo("uid", uid)
                .whereEqualTo("status", "IN-CART")
                .get().get();

        // Get document
        QueryDocumentSnapshot document = querySnapshot.getDocuments().getFirst();

        // Retrieve the products list
        Transactions transaction = document.toObject(Transactions.class);
        Map<String, Object> products = transaction.getProducts();

        // Update total price
        double price_to_deduct = 0.0;

        for (Map.Entry<String, Object> entry : products.entrySet()) {
            Map<String, Object> productDetails = (Map<String, Object>) entry.getValue();

            String record_swp_id = productDetails.get("swp_id").toString();
            double product_price = Double.parseDouble(productDetails.get("price").toString());
            int product_quantity = Integer.parseInt(productDetails.get("quantity").toString());
            price_to_deduct = product_price * product_quantity;

            // Remove product from map
            if (record_swp_id.equals(swp_id)) {
                products.remove(entry.getKey());
                break;
            }
        }

        if (!products.isEmpty()) {
            // Reorder products
            Map<String, Object> updatedProducts = new HashMap<>();
            int newIndex = 0;

            for (Map.Entry<String, Object> entry : products.entrySet()) {
                updatedProducts.put(String.valueOf(newIndex++), entry.getValue());
            }

            // Update record's quantity and price
            document.getReference().update("products", updatedProducts);
            document.getReference().update("total_price", Double.parseDouble(String.format("%.2f", transaction.getTotal_price() - price_to_deduct)));
        } else {
            // If no products by the wholesaler, delete transaction record and remove transaction id from cart record
            deleteTransaction(document.getId());
        }

        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("num_products", products.size());
        outputMap.put("tid", document.getId());
        outputMap.put("deduct", price_to_deduct);
        return outputMap;
    }

    @Override
    public TransactionsOrderedDTO getHistoryDetails(String orderId) throws ExecutionException, InterruptedException {
        DocumentReference transactionRef = firestore.collection(COLLECTION).document(orderId);
        ApiFuture<DocumentSnapshot> future = transactionRef.get();
        DocumentSnapshot document = future.get();

        TransactionsOrderedDTO transaction = new TransactionsOrderedDTO();
        if (document.exists()) {
            transaction.setStatus(Objects.requireNonNull(document.get("status")).toString());
            transaction.setUen(Objects.requireNonNull(document.get("uen")).toString());
            transaction.setUid(Objects.requireNonNull(document.get("uid")).toString());
            transaction.setTotal_price(Float.parseFloat(Objects.requireNonNull(document.get("total_price")).toString()));
            transaction.setRated(Boolean.parseBoolean(Objects.requireNonNull(document.get("rated")).toString()));

            Map<String, Object> productMap = (Map<String, Object>) document.get("products");
            ArrayList<Object> products = new ArrayList<>();
            for (int i = 0; i < Objects.requireNonNull(productMap).size(); i++) {
                String key = String.valueOf(i);
                Map<String, Object> newProduct = (Map<String, Object>) productMap.get(key);
                products.add(newProduct);
            }
            transaction.setProducts(products);
        }
        return transaction;
    }

    @Override
    public void updateTransactionRated(String tid) {
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);
        docRef.update("rated", true);
    }

    @Override
    public void updateTransactionStatus(Map<String, Object> data) {
        String tid = data.get("tid").toString();
        String status = data.get("status").toString();

        if (status.equals("IN-CART") || status.equals("PENDING-ACCEPTANCE") || status.equals("PENDING-COMPLETION")
                || status.equals("COMPLETED")) {
            DocumentReference docRef = firestore.collection(COLLECTION).document(tid);
            docRef.update("status", status);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Only accepts transaction statuses: IN-CART, PENDING-ACCEPTANCE, PENDING-COMPLETION, COMPLETED");
        }
    }

    @Override
    public void updateTransactionPrice(String tid, double updatedPrice) {
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);
        docRef.update("converted_price", Double.parseDouble(String.format("%.2f", updatedPrice)));
    }

    private void deleteTransaction(String tid) {
        firestore.collection(COLLECTION).document(tid).delete();
    }

}