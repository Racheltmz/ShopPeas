import {REACT_APP_BACKEND_CART} from "@env";

export const addCard = async (uid) => {
    const endpoint = "checkout/addpayment"; 
    try {
        const response = await apiClient.post(`${REACT_APP_BACKEND_CART}/${endpoint}`, {
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

export const getPayment = async (uid) => {
    const endpoint = "checkout/getpayment"; 
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
}

export const deletePayment = async (requestBody) => {
    const endpoint = "/shoppingcart/checkout/deletepayment"; 
    try {
        const response = await apiClient.patch(`${REACT_APP_BACKEND_AUTH}/${endpoint}`,requestBody, {
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