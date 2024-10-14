import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_AUTH } from '@env';

const authService = {
    register: async (token, userType, requestBody) => {
        console.log(token, userType, requestBody);
        const endpoint = userType === 'consumer' ? 'consumer' : 'wholesaler';
        const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/${endpoint}`, requestBody, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }
};

export default authService;