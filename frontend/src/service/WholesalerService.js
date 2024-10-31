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
    },
    getRating: async (token, uen) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_WHOLESALER}/rating/${uen}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
    rateWholesaler: async (token, uen, tid, rating) => {
        const requestBody = {
            "uen": uen,
            "tid": tid,
            "rating": rating
        }
        const response = await apiClient.patch(`${REACT_APP_BACKEND_WHOLESALER}/rate`, requestBody, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
    editProfile: async (token, requestBody) => {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_WHOLESALER}/profile/update`, requestBody, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
};

export default wholesalerService;