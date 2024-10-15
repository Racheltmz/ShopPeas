import axios from "axios";

const fetchProductData = async () => {
    const API_URL = "/products/all"; //
    const fullUrl = `http://localhost:8080${API_URL}`;
    console.log("Attempting to fetch from:", fullUrl);
    
    try {
        const response = await axios.get(fullUrl, {
            headers: {
                Authorization: "Bearer mx6376gHypOYG22wVJXF2YBHG393",
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
