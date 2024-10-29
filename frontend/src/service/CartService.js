import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_CART } from "@env";
import { REACT_APP_BACKEND_WHOLESALER} from "@env";

const cartService = {
    getCart : async (uid) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_CART}/view`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    },
    addToCart : async (uid, requestBody) => {
        // add quantity of a product to cart
        const response = await apiClient.post(`${REACT_APP_BACKEND_CART}/add`, requestBody, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    },
    updateCart : async (uid, requestBody) => {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_CART}/update`, requestBody, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    },
    deleteCartItem : async (uid, requestBody) => {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_CART}/deleteItem`, requestBody, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    }
}

export default cartService;