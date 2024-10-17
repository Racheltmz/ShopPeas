import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_CART} from '@env';

export const getCart = async (uid) => {
    const endpoint = "getCart"; 
    try {
        const response = await apiClient.get(`${REACT_APP_BACKEND_CART}/${endpoint}`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;

    } catch (error) {
        console.error("Error config:", JSON.stringify(error.config, null, 2));
        throw error;
    }
};

// export const addToCart = async (bodyData) => {
//     const endpoint = "/shoppingCart/addtocart"; 
//     try {
//         const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/${endpoint}`,bodyData, {
//             headers: {
//                 Authorization: `Bearer ${uid}`,
//                 Accept: "application/json",
//             },
//         });
//         return response.data;

//     } catch (error) {
//         console.error("Error config:", JSON.stringify(error.config, null, 2));
//         throw error;
//     }
// };

export const updateCart = async (bodyData) => {
    const endpoint = "/shoppingCart/addToCart/update"; 
    try {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_AUTH}/${endpoint}`,bodyData, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;

    } catch (error) {
        console.error("Error config:", JSON.stringify(error.config, null, 2));
        throw error;
    }
};

export const checkout = async (bodyData) => {
    const endpoint = "/transaction/checkout"; 
    try {
        const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/${endpoint}`,bodyData, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;

    } catch (error) {
        console.error("Error config:", JSON.stringify(error.config, null, 2));
        throw error;
    }
};




