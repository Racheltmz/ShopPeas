import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_CART} from '@env';

const cartService = {
    getCart : async (uid) => {
        try {
            const response = await apiClient.get(`${REACT_APP_BACKEND_CART}/view`, {
                headers: {
                    Authorization: `Bearer ${uid}`,
                    Accept: "application/json",
                },
            });
            return response.data;

        } catch (error) {
            console.error("Error config:", JSON.stringify(error.config, null, 2));
            throw error;
        }
    },
    addToCart : async (bodyData) => {
        try {
            const response = await apiClient.post(`${REACT_APP_BACKEND_CART}/add`,bodyData, {
                headers: {
                    Authorization: `Bearer ${uid}`,
                    Accept: "application/json",
                },
            });
            return response.data;

        } catch (error) {
            console.error("Error config:", JSON.stringify(error.config, null, 2));
            throw error;
        }
    },
    updateCart : async (bodyData) => {
        try {
            const response = await apiClient.patch(`${REACT_APP_BACKEND_CART}/update`,bodyData, {
                headers: {
                    Authorization: `Bearer ${uid}`,
                    Accept: "application/json",
                },
            });
            return response.data;

        } catch (error) {
            console.error("Error config:", JSON.stringify(error.config, null, 2));
            throw error;
        }
    },
    checkout : async (bodyData) => {
        const endpoint = "/transaction/checkout"; 
        try {
            const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/${endpoint}`,bodyData, {
                headers: {
                    Authorization: `Bearer ${uid}`,
                    Accept: "application/json",
                },
            });
            return response.data;

        } catch (error) {
            console.error("Error config:", JSON.stringify(error.config, null, 2));
            throw error;
        }
    }
}

export default cartService;