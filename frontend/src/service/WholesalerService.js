import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_WHOLESALER } from '@env';

const wholesalerService = {
    retrieveProfile: async (token) => {
        console.log(token);
        const response = await apiClient.get(`${REACT_APP_BACKEND_WHOLESALER}/profile`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }
};

export default wholesalerService;