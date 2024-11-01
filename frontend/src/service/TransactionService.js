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
    getTransactions: async (uid, uen, status) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_TRANSACTION}/get?uen=${uen}&status=${status}`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
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
            return response.data;
        } catch (error) {
            console.error('Update status error:', error);
            throw error;
        }
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