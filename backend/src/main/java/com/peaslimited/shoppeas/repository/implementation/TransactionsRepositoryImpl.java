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

/**
 * Implementation of TransactionRepository for managing transaction data in Firestore,
 * including methods to retrieve, add, update, and delete transactions.
 */
@Repository
public class TransactionsRepositoryImpl implements TransactionsRepository {

    private final String COLLECTION = "transactions";

    @Autowired
    private Firestore firestore;

    @Autowired
    private WholesalerProductService wholesalerProductService;

    /**
     * {@inheritDoc}
     *
     * Retrieves a transaction document by its TID and converts it to {@link TransactionsDTO}.
     *
     * @param tid the unique transaction ID
     * @return a {@link TransactionsDTO} containing transaction details, or null if not found
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Finds a cart transaction for the specified user (UID) and wholesaler (UEN) with status "IN-CART".
     *
     * @param uid the user's unique identifier
     * @param uen the wholesaler's unique entity number
     * @return a {@link Transactions} object representing the cart transaction, or null if not found
     * @throws ExecutionException
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Retrieves a list of transactions filtered by UEN and status.
     *
     * @param uen the wholesaler's unique entity number
     * @param status the status of the transaction
     * @return a list of {@link QueryDocumentSnapshot} objects matching the criteria
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @Override
    public List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uen", uen)
                .whereEqualTo("status", status).get();

        QuerySnapshot querySnapshot = query.get();

        return querySnapshot.getDocuments();
    }

     /**
     * {@inheritDoc}
     *
     * Extracts the product list from a transaction document.
     *
     * @param document the Firestore document snapshot of the transaction
     * @param cart a boolean indicating if the transaction is a cart transaction
     * @return an {@link ArrayList} of product details
     */
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

    /**
     * {@inheritDoc}
     *
     * Adds a new transaction to Firestore.
     *
     * @param transactionsDTO a {@link TransactionsDTO} containing transaction details
     * @return the ID of the newly created transaction
     */
    @Override
    public String addTransaction(TransactionsDTO transactionsDTO) {
        DocumentReference docRef = firestore.collection(COLLECTION).document();
        String tid = docRef.getId();
        docRef.set(transactionsDTO);
        return tid;
    }

    /**
     * {@inheritDoc}
     *
     * Updates a specific product within an existing transaction in Firestore.
     *
     * @param transaction the {@link Transactions} object representing the transaction to update
     * @param uid the user's unique identifier
     * @param data a {@link Map} containing the update data for the transaction's product
     */
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

    /**
     * {@inheritDoc}
     *
     * Updates the quantity of a specific product.
     *
     * @param uid the user's unique identifier
     * @param uen the wholesaler's unique entity number
     * @param swp_id the unique wholesaler product identifier 
     * @param newQuantity the new quantity of the product
     * @param price the price of the product
     * @throws ExecutionException
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Updates the product list in a transaction based on UID, UEN and swp_id, adjusting the total price as necessary.
     *
     * @param uid the user's unique identifier
     * @param uen the wholesaler's unique entity number
     * @param swp_id the wholesaler product's unique identifier
     * @return a {@link Map} containing the updated product list and the deducted price
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Retrieves historical transaction details for a specific order ID.
     *
     * @param orderId the unique identifier of the order
     * @return a {@link TransactionsOrderedDTO} containing ordered transaction details
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
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

    /**
     * {@inheritDoc}
     *
     * Marks a transaction as rated by updating the "rated" field in Firestore.
     *
     * @param tid the unique transaction ID
     */
    @Override
    public void updateTransactionRated(String tid) {
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);
        docRef.update("rated", true);
    }

    /**
     * {@inheritDoc}
     *
     * Updates the status of a transaction in Firestore if the status is valid.
     *
     * @param data a {@link Map} containing the transaction's TID and new status
     * @throws ResponseStatusException 
     */
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

     /**
     * {@inheritDoc}
     *
     * Updates the price of a transaction by setting a new value for "converted_price" in Firestore.
     *
     * @param tid the unique transaction ID
     * @param updatedPrice the new price to set for the transaction
     */
    @Override
    public void updateTransactionPrice(String tid, double updatedPrice) {
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);
        docRef.update("converted_price", Double.parseDouble(String.format("%.2f", updatedPrice)));
    }

    /**
     * Deletes a transaction from the Firestore collection based on its TID.
     *
     * @param tid the unique transaction ID
     */
    private void deleteTransaction(String tid) {
        firestore.collection(COLLECTION).document(tid).delete();
    }

}