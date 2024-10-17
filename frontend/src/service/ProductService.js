import apiClient from "../api/apiClient";
import {  REACT_APP_BACKEND_PRODUCT } from '@env';

// API calls for customers
export const fetchProductData = async (uid) => {
    const endpoint = "all"; 
    try {
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/${endpoint}`, {
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
};

export const getWholesalerByProduct = async (pid, uid) => {
    const endpoint = `${pid}`;
    try {
        const response = await apiClient.post(`${REACT_APP_BACKEND_PRODUCT}/${endpoint}`, {
            headers: {
                'Authorization': `Bearer ${uid}`
            }
        });
    } catch(error) {

    }
    
}


