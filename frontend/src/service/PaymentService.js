import apiClient from '../api/apiClient'
import { REACT_APP_BACKEND_PAYMENT } from "@env";

const paymentService = {
    addCard : async (uid, requestBody) => {
        const endpoint = "add"; 
        try {
            const response = await apiClient.post(`${REACT_APP_BACKEND_PAYMENT}/${endpoint}`, requestBody,{
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
    getPayment : async (uid) => {
        const endpoint = "get"; 
        try {
            const response = await apiClient.get(`${REACT_APP_BACKEND_PAYMENT}/${endpoint}`, {
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
    deletePayment: async (uid, cardNumber) => {
        const endpoint = "delete"; 
        try {
            const response = await apiClient.patch(`${REACT_APP_BACKEND_PAYMENT}/${endpoint}`,{ card_no: cardNumber },
                {
                    headers: {
                        Authorization: `Bearer ${uid}`,
                        Accept: "application/json",
                    }
                }
            );
            return response.data;
        } catch (error) {
            console.error("Error config:", JSON.stringify(error.config, null, 2));
            throw error;
        }
    }
}
 
export default paymentService; 