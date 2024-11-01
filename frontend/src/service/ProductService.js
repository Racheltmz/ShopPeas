import apiClient from "../api/apiClient";
import {  REACT_APP_BACKEND_PRODUCT } from '@env';

const productService = {
    fetchProductData: async (uid) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/all`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    },
    fetchProductImage: async (uid, name) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/image`, {  
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
            params: {
                name: name 
            }
        });
        return response.data;
    },
    fetchProductData: async (uid) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/all`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
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
    },
    addWholesalerProduct: async (token, requestBody) => {
        const response = await apiClient.post(`${REACT_APP_BACKEND_PRODUCT}/add`, requestBody, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
    editWholesalerProduct: async (token, swp_id, requestBody) => {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_PRODUCT}/update/${swp_id}`, requestBody, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
    deleteWholesalerProduct: async (token, swp_id) => {
        const response = await apiClient.delete(`${REACT_APP_BACKEND_PRODUCT}/delete/${swp_id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }
};

export default productService;