package com.peaslimited.shoppeas.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peaslimited.shoppeas.service.OneMapService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class OneMapServiceImpl implements OneMapService {

    // Base URLs
    private final String SEARCH_URL = "https://www.onemap.gov.sg/api/common/elastic/search";
    private final String ROUTING_URL = "https://www.onemap.gov.sg/api/public/routingsvc/route";

    // Replace API token on 20/10
    private final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4ZjhlZDAwNTI2ZDk0ZTdlNGQ2YjI2NDhjMWEyM2E1OSIsImlzcyI6Imh0dHA6Ly9pbnRlcm5hbC1hbGItb20tcHJkZXppdC1pdC1uZXctMTYzMzc5OTU0Mi5hcC1zb3V0aGVhc3QtMS5lbGIuYW1hem9uYXdzLmNvbS9hcGkvdjIvdXNlci9wYXNzd29yZCIsImlhdCI6MTcyOTA5NTMxNCwiZXhwIjoxNzI5MzU0NTE0LCJuYmYiOjE3MjkwOTUzMTQsImp0aSI6IjRucUh3RUJjOTR4aVZnVVgiLCJ1c2VyX2lkIjo0OTA5LCJmb3JldmVyIjpmYWxzZX0.QuvyPsLHoVLosiByklx_fohHOUXSpJe6MPYAmTw483w";


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OneMapServiceImpl() {
        this.restTemplate = new RestTemplate();  // Used for making HTTP requests
        this.objectMapper = new ObjectMapper();  // Used for parsing JSON responses
    }

    // URL calling works
    @Override
    public String getCoordinates(String postalCode) throws InterruptedException, ExecutionException {
        // Build the search URL
        String url = SEARCH_URL + "?searchVal=" + postalCode + "&returnGeom=Y&getAddrDetails=Y";

        // Add authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Extract the coordinates from the JSON response
        return extractCoordinates(response.getBody());
    }

    // URL calling works
    @Override
    public String calculateDrivingTime(String startCoordinates, String endCoordinates) throws InterruptedException, ExecutionException {
        // Split start and end coordinates (assuming they are formatted as "latitude,longitude")
        String[] start = startCoordinates.split(",");
        String[] end = endCoordinates.split(",");

        // Build the URL for routing API call with driving as routeType
        String url = ROUTING_URL + "?start=" + start[0] + "," + start[1]
                + "&end=" + end[0] + "," + end[1]
                + "&routeType=drive"
                + "&date=10-17-2024"
                + "&time=15:45:30"
                + "&mode=TRANSIT";

        // Add authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Extract driving time from the JSON response
        return extractDrivingTime(response.getBody());
    }

    private String extractCoordinates(String response) {
        try {
            // Parse the JSON response
            JsonNode rootNode = objectMapper.readTree(response);

            // Navigate to the "results" array
            JsonNode results = rootNode.path("results");

            // Check if there is at least one result
            if (results.isArray() && !results.isEmpty()) {
                JsonNode firstResult = results.get(0);
                String latitude = firstResult.path("LATITUDE").asText();
                String longitude = firstResult.path("LONGITUDE").asText();

                return latitude + "," + longitude;  // Return as "latitude,longitude"
            } else {
                return null;  // No results found
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractDrivingTime(String response) {
        try {
            // Parse the JSON response
            JsonNode rootNode = objectMapper.readTree(response);

            // Navigate to the "route_summary" object
            JsonNode routeSummary = rootNode.path("route_summary");

            if (!routeSummary.isMissingNode()) {
                // Extract the total travel time in seconds
                int totalTimeInSeconds = routeSummary.path("total_time").asInt();

                // Convert time from seconds to minutes
                int totalTimeInMinutes = totalTimeInSeconds / 60;

                // Extract the total distance in Meters
                float totalDistanceInMeters = routeSummary.path("total_distance").asInt();

                // Convert from M to Km
                float totalDistanceInKm = totalDistanceInMeters/1000;

                // Return the driving time in minutes
                return "Time: " + totalTimeInMinutes + " Minutes, Distance: " + totalDistanceInKm + "Km";
            } else {
                return null;  // No route found
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

