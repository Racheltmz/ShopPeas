import apiClient from "../api/apiClient";
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
};

export default transactionService;