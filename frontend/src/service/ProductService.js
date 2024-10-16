import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_PRODUCT } from '@env';

const fetchProductData = async (uid) => {
    const endpoint = "/products/all"; 
    try {
        const response = await apiClient.get(`${REACT_APP_BACKEND_AUTH}/${endpoint}`, {
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

export { fetchProductData };
