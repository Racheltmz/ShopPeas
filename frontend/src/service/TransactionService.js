import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_TRANSACTION } from '@env';

const transactionService = {
    getTransactions: async (uid, uen, status) => {
        console.log('Calling API with:', { uid, uen, status });
        const response = await apiClient.get(`${REACT_APP_BACKEND_TRANSACTION}/gettransactionsbyuen?uen=${uen}&status=${status}`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        console.log('TransactionService - Raw API response:', response.data);
        return response.data;
    },

    updateStatus: async (uid, tid, newStatus) => {
        try {
            const response = await apiClient.patch(
                `${REACT_APP_BACKEND_TRANSACTION}/updatetransactionstatus`,
                {
                    tid: tid,
                    status: newStatus
                },
                {
                    headers: {
                        Authorization: `Bearer ${uid}`,
                        Accept: 'application/json'
                    }
                }
            );
            console.log('Status update success:', response);
            return response.data;
        } catch (error) {
            console.error('Update status error:', error);
            throw error;
        }
    },
};

export default transactionService;