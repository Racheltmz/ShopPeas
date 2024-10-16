import axios from "axios";
import { useUserStore } from "../lib/userStore";



const fetchProductData = async (uid) => {
    const API_URL = "/products/all"; //
    const fullUrl = `http://localhost:8080${API_URL}`;
    console.log("Attempting to fetch from:", fullUrl);
    try {
        const response = await axios.get(fullUrl, {
            headers: {
                Authorization: `Bearer ${uid}`,
                Accept: "application/json",
            },
            timeout: 10000, 
        });
        return response.data;
    } catch (error) {
        console.error("Error config:", JSON.stringify(error.config, null, 2));
        throw error;
    }
};

export { fetchProductData };
