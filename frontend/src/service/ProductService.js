import apiClient from "../api/apiClient";
import {  REACT_APP_BACKEND_PRODUCT } from '@env';

const productService = {
    fetchProductData: async (uid) => {
        try {
            const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/all`, {
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
    getDetailsByPID: async (token, pid) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/${pid}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },

    getProductsByUEN: async (token) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/wholesaler`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }
};

export default productService;