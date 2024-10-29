package com.peaslimited.shoppeas.service.implementation;

import com.google.cloud.firestore.DocumentSnapshot;
import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.model.ShoppingCart;
import com.peaslimited.shoppeas.model.Transactions;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderHistoryService orderService;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private WholesalerService wholesalerService;
    @Autowired
    private WholesalerAddressService wholesalerAddressService;
    @Autowired
    private WholesalerProductService wholesalerProductService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Map<String, Object> getCartByUID(String uid) throws ExecutionException, InterruptedException {
        ShoppingCartDTO cart = cartRepository.findByUID(uid);
        assert cart != null;

        if (cart != null) {
            Map<String, Object> returnCart = new HashMap<>();
            ArrayList<String> orderList = cart.getOrders();
            ArrayList<Object> cartTransactions = new ArrayList<>();

            for (String tid : orderList) {
                Map<String, Object> transactionMap = new HashMap<>();
                TransactionsDTO transaction = transactionsService.findByTID(tid);
                String uen = transaction.getUen();
                WholesalerDTO wholesaler = wholesalerService.getWholesalerUID(uen);
                String wholesalerName = wholesaler.getName();
                WholesalerAddressDTO wholesalerAddress = wholesalerAddressService.getWholesalerAddress(uen);

                Map<String, Object> productsMapOld = transaction.getProducts();
                ArrayList<Object> itemsList = new ArrayList<>();

                for (int i = 0; i< productsMapOld.size(); i++) {
                    Map<String, Object> itemsMap = new HashMap<>();
                    String index = Integer.toString(i);
                    Map<String, Object> productsMap = (Map<String, Object>) productsMapOld.get(index);

                    Long q = (Long) productsMap.get("quantity");
                    int quantity = q.intValue();
                    String swp_id = productsMap.get("swp_id").toString();

                    WholesalerProductDTO wholesalerProduct = wholesalerProductService.getBySwp_id(swp_id);
                    double unit_price = wholesalerProduct.getPrice();
                    String pid = wholesalerProduct.getPid();
                    ProductDTO product = productService.getProductById(pid);
                    String productName = product.getName();
                    String imageURL = product.getImage_url();

                    itemsMap.put("swp_id", swp_id);
                    itemsMap.put("name", productName);
                    itemsMap.put("quantity", quantity);
                    itemsMap.put("price", unit_price);
                    itemsMap.put("image_url", imageURL);

                    itemsList.add(itemsMap);
                }
                transactionMap.put("uen", uen);
                transactionMap.put("wholesaler", wholesalerName);
                transactionMap.put("location", wholesalerAddress);
                transactionMap.put("items", itemsList);

                cartTransactions.add((transactionMap));
            }
            returnCart.put("cart", cartTransactions);
            return returnCart;
        }
        return null;
    }

    @Override
    public void addCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        // Get order data
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        double total_price = Double.parseDouble(data.get("total_price").toString());

        if (quantity <= 0) {
            System.out.println("Error! Invalid quantity");
            return;
        } else if (wholesalerProductService.getBySwp_id(swp_id) == null) {
            System.out.println("Error! Wholesaler product does not exist");
            return;
        }

        //ACTION: ADDS TRANSACTION RECORD
        String tid = transactionsService.updateTransactionProduct(uid, data);
        // Add cart record
        String cid = cartRepository.findCIDByUID(uid);
        if (cid == null) {
            ArrayList<String> orders = new ArrayList<>();
            orders.add(tid);
            cartRepository.addCart(new ShoppingCartDTO(orders, uid, total_price));
        } else {
            cartRepository.updateCartWithOrder(cid, tid, quantity, total_price);
        }
    }

    @Override
    public void updateCartQuantity(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        String uen = data.get("uen").toString();
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        double price = Double.parseDouble(data.get("price").toString());

        transactionsRepository.updateProductQuantity(uid, uen, swp_id, quantity, price);
        String cid = cartRepository.findCIDByUID(uid);
        cartRepository.updateCartPrice(cid, price);
    }

    @Override
    public void deleteCartProduct(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        String uen = data.get("uen").toString();
        String swp_id = data.get("swp_id").toString();

        Map<String, Object> outputMap = transactionsRepository.updateProductList(uid, uen, swp_id);
        String cid = cartRepository.findCIDByUID(uid);

        cartRepository.updateCartPrice(cid, Double.parseDouble(outputMap.get("deduct").toString()) * -1);

        // If there are no products belonging to the wholesaler, remove transaction from cart record;
        if (Integer.parseInt(outputMap.get("num_products").toString()) == 0) {
            cartRepository.deleteTransaction(cid, outputMap.get("tid").toString());
        }
    }


//    @Override
//    public void createCart(ShoppingCartDTO cartDTO) {
//        cartRepository.addByCID(cartDTO);
//    }
//
//    @Override
//    public ShoppingCart getCartByUID_NonDTO(String UID) throws ExecutionException, InterruptedException {
//        return cartRepository.findByUID_NonDTO(UID);
//    }
//
//
//    @Override
//    public void updateCart(String cid, Map<String, Object> data) throws ExecutionException, InterruptedException {
//        cartRepository.updateCart(cid, data);
//    }
//
//    @Override
//    public void deleteWholeCart(String cid) throws ExecutionException, InterruptedException {
//        cartRepository.deleteWholeCart(cid);
//    }
//
//    public void deleteItemByOID(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
//        cartRepository.deleteCartItem(uid, data);
//    }

}
