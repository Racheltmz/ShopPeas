package com.peaslimited.shoppeas.repository.implementation;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.Firestore;
import com.peaslimited.shoppeas.dto.TransactionsDTO;
import com.peaslimited.shoppeas.dto.WholesalerProductDTO;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.WholesalerProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Repository
public class TransactionsRepositoryImpl implements TransactionsRepository {

    private final String COLLECTION = "transactions";
    @Autowired
    private Firestore firestore;

    @Autowired
    private WholesalerProductService wholesalerProductService;

    @Override
    public TransactionsDTO getTransactionByUID(String uid, String status) throws ExecutionException, InterruptedException
    {
        DocumentSnapshot document = findDocByUIDandStatus(uid, status);

        if(document != null)
        {
            System.out.println(document);
            String uen = document.get("uen").toString();
            double total_price = (double) document.get("total_price");
            double price = (double) total_price;
            ArrayList<Object> products = (ArrayList<Object>) document.get("products");
            return new TransactionsDTO(products,status,price,uen,uid);
        }
        return null;
    }

    @Override
    public DocumentSnapshot findDocByUIDandStatus(String UID, String status) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uid", UID).get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();


        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        DocumentSnapshot document = null;
        // Check if any documents match
        for(int i = 0; i< documents.size(); i++)
        {
            document = documents.get(i);
            if(document.get("status").equals(status))
            {
                return document;
            }
            else document = null;
        }
        /*DocumentSnapshot document = null;
        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            document = documents.getFirst();
        }*/

        return document;
    }

    @Override
    public List<QueryDocumentSnapshot> getDocByUENAndStatus(String uen, String status) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uen", uen)
                .whereEqualTo("status", status).get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        return documents;
    }

    @Override
    public DocumentSnapshot getDocByUENAndWName(String uen, String uid) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uen", uen)
                .whereEqualTo("uid", uid).whereEqualTo("status", "IN-CART").get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        DocumentSnapshot document = null;
        if (!documents.isEmpty()) {
            // Get the first matching document and return its ID
            document = documents.getFirst();
        }
        return document;
    }


    @Override
    public void createTransaction(TransactionsDTO transactionsDTO)
    {
        firestore.collection(COLLECTION).document().set(transactionsDTO);
    }

    @Override
    public void updateTransaction(String tid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Update an existing document
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);

        // Update fields
        for (String key : data.keySet()) {
            docRef.update(key, data.get(key));
        }
    }

    @Override
    public void updateTransactionProduct(Map<String, Object> data, String uid, String status) throws ExecutionException, InterruptedException {
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        String uen = data.get("uen").toString();
        DocumentSnapshot transaction = findDocByUIDandStatus(uid, status);
        System.out.println(transaction);

        double price = getProductPrice(swp_id,uen);
        double total_price = (double) transaction.get("total_price");
        double oldprice = (double) total_price;

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("quantity", quantity);
        productMap.put("swp_id", swp_id);

        ArrayList<Object> productsList = (ArrayList<Object>) transaction.get("products");
        productsList.add(productMap);

        String id = transaction.getId();
        DocumentReference docRef = firestore.collection(COLLECTION).document(id);
        docRef.update("total_price", price+oldprice);
        docRef.update("products", productsList);

    }

    @Override
    public void updateTransactionStatus(Map<String, Object> data)
    {
        String tid = data.get("tid").toString();
        String status = data.get("status").toString();

        if(status.equals("IN-CART") || status.equals("PENDING-ACCEPTANCE") || status.equals("PENDING-COMPLETION") || status.equals("COMPLETED"))
        {
            DocumentReference docRef = firestore.collection(COLLECTION).document(tid);
            docRef.update("status", status);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only accepts transaction statuses: IN-CART, PENDING-ACCEPTANCE, PENDING-COMPLETION, COMPLETED");
        }
    }

    @Override
    public List<QueryDocumentSnapshot> findDocListByUID(String UID) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = firestore.collection(COLLECTION).whereEqualTo("uid", UID).get();

        // Asynchronously retrieve the document
        QuerySnapshot querySnapshot = query.get();


        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        return documents;
    }

    public double getProductPrice(String swp_id, String uen) throws ExecutionException, InterruptedException {
        WholesalerProductDTO wholesalerProd =  wholesalerProductService.getBySwp_id(swp_id);
        return wholesalerProd.getPrice();
    }

    @Override
    public TransactionsDTO findByTID(String tid) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION).document(tid);

        // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        // Convert document to Consumer object
        TransactionsDTO transactionsDTO = new TransactionsDTO();
        if (document.exists()) {
            //System.out.println(document);
            transactionsDTO.setStatus(document.get("status").toString());
            transactionsDTO.setUen(document.get("uen").toString());
            transactionsDTO.setUid(document.get("uid").toString());
            transactionsDTO.setTotal_price(Float.parseFloat(document.get("total_price").toString()));

            Map<String, Object> productMap = (Map<String, Object>) document.get("products");
            ArrayList<Object> products = new ArrayList<>();
            for(int i =0; i< productMap.size(); i++)
            {
                String key = String.valueOf(i);
                Map<String, Object> newProduct = (Map<String, Object>) productMap.get(key);
                products.add(newProduct);
            }
            //System.out.println(products);
            transactionsDTO.setProducts(products);

            return transactionsDTO;
        }
        else return null;

    }

    @Override
    public ArrayList<Object> getProductListfromTransaction(DocumentSnapshot document, boolean cart) throws ExecutionException, InterruptedException {
        ArrayList<Object> productList = (ArrayList<Object>) document.get("products");
        ArrayList<Object> returnProdList = new ArrayList<>();

        for(int i = 0; i < productList.size(); i++)
        {
            Map<String, Object> docProduct = (Map<String, Object>) productList.get(i);
            Map<String, Object> product = new HashMap<>();
            String swpid = docProduct.get("swp_id").toString();
            System.out.println(swpid);
            System.out.println("--");
            int quantity = Integer.parseInt(docProduct.get("quantity").toString());

            String productName = wholesalerProductService.getWholesalerProductName(swpid);
            product.put("name", productName);
            product.put("quantity", quantity);

            if(cart)
            {
                //ACTION: get wholesaler name, unit price
            }
            returnProdList.add(product);
        }
        return returnProdList;
    }
}