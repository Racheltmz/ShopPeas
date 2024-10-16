import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_PRODUCT } from '@env';

const productSerevice = {
    getDetailsByPID: async (token, pid) => {
        console.log(token, pid)
        const response = await apiClient.get(`${REACT_APP_BACKEND_PRODUCT}/${pid}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        return response.data;
    }
};

export default productSerevice;