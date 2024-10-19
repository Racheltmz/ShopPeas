import axios from 'axios';
import { REACT_APP_BACKEND_API } from '@env';

// Axios instance
const apiClient = axios.create({
    baseURL: REACT_APP_BACKEND_API
});

export default apiClient;