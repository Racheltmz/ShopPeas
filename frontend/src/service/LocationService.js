import apiClient from "../api/apiClient";
import { REACT_APP_ONEMAP_API_KEY } from '@env';

const locationService = {
    getCoordinates: async (postalCode) => {
        const response = await apiClient.get(`https://www.onemap.gov.sg/api/common/elastic/search?searchVal=${postalCode}&returnGeom=Y&getAddrDetails=Y&pageNum=1`, {
            headers: {
                'Authorization': `Bearer ${REACT_APP_ONEMAP_API_KEY}`
            }
        });
        return response.data;
    },
    getRoute: async (start, end) => {
        const response = await apiClient.get(`https://www.onemap.gov.sg/api/public/routingsvc/route?start=${start[0]},${start[1]}&end=${end[0]},${end[1]}&routeType=drive&date=10-17-2024&time=15:45:30&mode=TRANSIT`, {
            headers: {
                'Authorization': `Bearer ${REACT_APP_ONEMAP_API_KEY}`
            }
        });
        return response.data;
    }
};

export default locationService;