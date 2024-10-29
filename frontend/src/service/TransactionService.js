import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_TRANSACTION } from '@env';
import { REACT_APP_BACKEND_HISTORY } from '@env';

const transactionService = {
    viewOrderHistory: async (uid) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_HISTORY}/view`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    },
    checkout : async (uid, requestBody) => {
        const response = await apiClient.post(`${REACT_APP_BACKEND_TRANSACTION}/checkout`, requestBody, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    }
};

export default transactionService;