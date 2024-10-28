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
    // TODO: is this being used?
    getUenByName: async (name, uid) => {
        const requestBody = {
            "name": name,
        }
        const response = await apiClient.patch(`${REACT_APP_BACKEND_WHOLESALER}/getUen`, requestBody, {
            headers: {
                'Authorization': `Bearer ${uid}`
            }
        });
        return response.data;
    }
};

export default wholesalerService;