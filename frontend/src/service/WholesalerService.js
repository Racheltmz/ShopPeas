import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_WHOLESALER } from '@env';

const wholesalerService = {
    viewWholesaler: async (token, uen) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_WHOLESALER}/view/${uen}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
    retrieveProfile: async (token) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_WHOLESALER}/profile`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }
};

export default wholesalerService;