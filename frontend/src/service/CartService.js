import apiClient from "../api/apiClient";
import { REACT_APP_BACKEND_CART } from "@env";
import { REACT_APP_BACKEND_WHOLESALER} from "@env";

const cartService = {
    getCart : async (uid) => {
        const response = await apiClient.get(`${REACT_APP_BACKEND_CART}/view`, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
        });
        return response.data;
    },
    addToCart : async (data, uid) => {
        let swpId;

        const getSwpIdBodyData = JSON.stringify({
            uen: data["uen"],
            product_name: data["productName"]
        })

        // get wholesaler product ID
        try {
            const response = await apiClient.get(`${REACT_APP_BACKEND_CART}/getswpid`, {
                headers: {
                    Authorization: `Bearer ${uid}`,
                    Accept: "application/json",
                    'Content-Type': 'application/json'
                },
                data: getSwpIdBodyData,
            });
            swpId = response.data;
        } catch (error) {
            console.error("Error config:", JSON.stringify(error.config, null, 2));
            throw error;
        }

        const bodyData = { "swp_id": swpId, "uen": data["uen"], "quantity": data["quantity"] };

        // add quantity of a product to cart
        try {
            const response = await apiClient.post(`${REACT_APP_BACKEND_CART}/add`,bodyData, {
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
    },

    updateCart : async (bodyData, uid) => {
        try {
            const response = await apiClient.patch(`${REACT_APP_BACKEND_CART}/update`,bodyData, {
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
    },
    checkout : async (bodyData, uid) => {
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
    }
}

export default cartService;