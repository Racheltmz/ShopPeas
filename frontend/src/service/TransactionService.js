import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_TRANSACTIONS } from '@env';

const transactionService = {
    getTransactions: async (uid, uen, status) => {
        console.log('Calling API with:', { uid, uen, status });
        const response = await apiClient.get(`${REACT_APP_BACKEND_TRANSACTIONS}/gettransactionsbyuen?uen=${uen}&status=${status}`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
            
        });
        console.log('success');
        return response.data;
    },
};

export default transactionService;