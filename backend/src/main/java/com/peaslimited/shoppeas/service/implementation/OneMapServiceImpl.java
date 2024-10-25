package com.peaslimited.shoppeas.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peaslimited.shoppeas.service.OneMapService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class OneMapServiceImpl implements OneMapService {

    // Base URLs
    private final String SEARCH_URL = "https://www.onemap.gov.sg/api/common/elastic/search";
    private final String ROUTING_URL = "https://www.onemap.gov.sg/api/public/routingsvc/route";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OneMapServiceImpl() {
        this.restTemplate = new RestTemplate();  // Used for making HTTP requests
        this.objectMapper = new ObjectMapper();  // Used for parsing JSON responses
    }

    // Replace API token on 20/10
    @PostConstruct
    public String getApiKeyPath() throws IOException {
        // Load resource from classpath
        ClassPathResource resource = new ClassPathResource("config/onemapapi.json");

        // Open the input stream for the file inside the classpath
        String apiKey;
        try (InputStream serviceAccount = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(serviceAccount);

            apiKey = jsonNode.get("apiKey").asText();
        }
        return apiKey;
    }

    // URL calling works
    @Override
    public String getCoordinates(String postalCode) throws IOException {
        // Build the search URL
        String url = SEARCH_URL + "?searchVal=" + postalCode + "&returnGeom=Y&getAddrDetails=Y";

        // Add authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getApiKeyPath());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Extract the coordinates from the JSON response
        return extractCoordinates(response.getBody());
    }

    @Override
    public String calculateDrivingTime(String startCoordinates, String endCoordinates) throws IOException {
        // Split start and end coordinates (assuming they are formatted as "latitude,longitude")
        String url = getString(startCoordinates, endCoordinates);

        // Add authorization header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getApiKeyPath());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Extract driving time from the JSON response
        return extractDrivingTime(response.getBody());
    }

    private String getString(String startCoordinates, String endCoordinates) {
        String[] start = startCoordinates.split(",");
        String[] end = endCoordinates.split(",");

        // Build the URL for routing API call with driving as routeType
        return ROUTING_URL + "?start=" + start[0] + "," + start[1]
                + "&end=" + end[0] + "," + end[1]
                + "&routeType=drive"
                + "&date=10-17-2024"
                + "&time=15:45:30"
                + "&mode=TRANSIT";
    }

    private String extractCoordinates(String response) throws JsonProcessingException {
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
    }

    private String extractDrivingTime(String response) throws IOException {
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
    }
}

