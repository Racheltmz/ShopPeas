import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_AUTH } from '@env';

export const register = async (userType, requestBody) => {
    if (userType == 'consuemr') {
        const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/consumer`, requestBody);
        return response.data;
    } else if (userType == 'wholesaler') {
        const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/wholesaler`, requestBody);
        return response.data;
    }
}