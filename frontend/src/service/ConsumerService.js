import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_CONSUMER } from '@env';

const consumerService = {
    viewProfile: async (token) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_CONSUMER}/profile`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },

    editProfile: async (token, requestBody) => {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_CONSUMER}/profile/update`, requestBody, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    },
}

export default consumerService;