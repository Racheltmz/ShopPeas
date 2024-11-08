package com.peaslimited.shoppeas.service.implementation;

import com.peaslimited.shoppeas.dto.*;
import com.peaslimited.shoppeas.repository.CartRepository;
import com.peaslimited.shoppeas.repository.TransactionsRepository;
import com.peaslimited.shoppeas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link CartService} interface that handles operations related to shopping cart functionality.
 * This service provides methods to retrieve, add, update, and delete cart items.
 */
@Service
public class CartServiceImpl implements CartService {

    /**
     * Repository for interacting with the cart data.
     */
    @Autowired
    private CartRepository cartRepository;
    /**
     * Service for handling transactions.
     */
    @Autowired
    private TransactionsService transactionsService;

    /**
     * Service pertaining to wholesaler information.
     */
    @Autowired
    private WholesalerService wholesalerService;
    /**
     * Service pertaining to wholesaler address information.
     */
    @Autowired
    private WholesalerAddressService wholesalerAddressService;
    /**
     * Service pertaining to wholesaler products.
     */
    @Autowired
    private WholesalerProductService wholesalerProductService;
    /**
     * Service pertaining to products.
     */
    @Autowired
    private ProductService productService;
    /**
     * Service pertaining to transactions.
     */
    @Autowired
    private TransactionsRepository transactionsRepository;


    /**
     * Retrieves the shopping cart for a user by their user ID (UID).
     * @param uid The unique identifier of the user whose cart contents are to be retrieved.
     * @return A map containing the cart details, including a list of transactions and their associated products.
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
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

    /**
     * Adds an item to the user's cart.
     *
     * @param uid The user ID of the user adding the item to their cart.
     * @param data A map containing the data of the product to be added (e.g., swp_id, quantity, total price).
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws ResponseStatusException If there are validation errors, such as invalid product or quantity.
     */
    @Override
    public void addCartItem(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException,ResponseStatusException {
        // Get order data
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        double total_price = Double.parseDouble(data.get("total_price").toString());

        if (wholesalerProductService.getBySwp_id(swp_id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Wholesaler product does not exist");
        } else if (!checkQuantity(quantity)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Invalid quantity");
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

    /**
     * Updates the quantity of a product in the user's cart.
     *
     * @param uid The user ID of the user updating their cart.
     * @param data A map containing the data for the update (e.g., swp_id, quantity, price).
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     * @throws ResponseStatusException If the wholesaler or product does not exist, or if the cart is not found.
     */
    @Override
    public void updateCartQuantity(String uid, Map<String, Object> data) throws ExecutionException, InterruptedException {
        String uen = data.get("uen").toString();
        String swp_id = data.get("swp_id").toString();
        int quantity = Integer.parseInt(data.get("quantity").toString());
        double price = Double.parseDouble(data.get("price").toString());

        if (!wholesalerService.UENExists(uen)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Wholesaler does not exist");
        }
        if (wholesalerProductService.getBySwp_id(swp_id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Wholesaler product does not exist");
        }
        String cid = cartRepository.findCIDByUID(uid);
        if (cid == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error! Cart does not exist");
        }
        transactionsRepository.updateProductQuantity(uid, uen, swp_id, quantity, price);
        cartRepository.updateCartPrice(cid, price);
    }

    /**
     * Deletes a product from the user's cart.
     *
     * @param uid The user ID of the user deleting the product.
     * @param data A map containing the data of the product to be deleted (e.g., swp_id, uen).
     * @throws ExecutionException If an error occurs while retrieving data asynchronously.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
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

    /**
     * Checks if the quantity in addToCart() is valid (i.e., quantity >=0)
     * @param quantity The quantity of a product being added to cart.
     * @return True if quantity is valid, false otherwise.
     */
    @Override
    public boolean checkQuantity(int quantity) {
        return quantity >= 0;
    }

}
