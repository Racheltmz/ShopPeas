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

    addProduct: async (token, newProductData) => {
        const response = await apiClient.post(`${REACT_APP_BACKEND_PRODUCT}/add`, newProductData, {
            headers: {
                'Authorization': `Bearer ${token}`
            },
        });
        return response.data;
    },

    updateProductByUen: async (token, uen, updatedProduct) => {
        const getSwpidBodyData = {
            "product_name": updatedProduct.name,
            "uen": uen,
        }
        console.log(getSwpidBodyData);
        // get swpid
        const swpid = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/getswpid`, {
            headers: {
                'Authorization': `Bearer ${token}`
            },
            data: getSwpidBodyData
        });

        console.log(getSwpidBodyData);
    }
};

export default productService;