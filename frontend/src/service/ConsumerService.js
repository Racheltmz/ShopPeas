import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_CONSUMER } from '@env';

export const viewProfile = async () => {
    console.log('edit this');
    // how to add authorization header if you need to
    // const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/${endpoint}`, requestBody, {
    //     headers: {
    //         'Authorization': `Bearer ${token}`
    //     }
    // });
    
}


export const addCard = async (uid) => {
    const endpoint = "/shoppingcart/checkout/addpayment"; 
    try {
        const response = await apiClient.post(`${REACT_APP_BACKEND_AUTH}/${endpoint}`, {
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
}

// export const addCard = async (uid) => {
//     const endpoint = "/shoppingcart/checkout/addpayment"; 
//     try {
//         const response = await apiClient.get(`${REACT_APP_BACKEND_AUTH}/${endpoint}`, {
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
// }

