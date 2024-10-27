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
    }
}

export default consumerService;