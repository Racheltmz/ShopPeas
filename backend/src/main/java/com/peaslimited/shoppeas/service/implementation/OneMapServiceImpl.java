package com.peaslimited.shoppeas.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peaslimited.shoppeas.dto.LocationDTO;
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

/**
 * This class is an implementation of the {@link OneMapService} interface, and it interacts with the OneMap API
 * to provide functionalities such as retrieving coordinates from a postal code and calculating the driving time between
 * two sets of coordinates (consumer and wholesaler locations).
 */
@Service
public class OneMapServiceImpl implements OneMapService {

    // Base URLs
    private final String SEARCH_URL = "https://www.onemap.gov.sg/api/common/elastic/search";
    private final String ROUTING_URL = "https://www.onemap.gov.sg/api/public/routingsvc/route";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Constructor to initialize the {@link RestTemplate} and {@link ObjectMapper}.
     * {@code RestTemplate} is used for making HTTP requests, and {@code ObjectMapper} is used
     * for parsing JSON responses.
     */
    public OneMapServiceImpl() {
        this.restTemplate = new RestTemplate();  // Used for making HTTP requests
        this.objectMapper = new ObjectMapper();  // Used for parsing JSON responses
    }

    /**
     * Loads the API key from a configuration file on the classpath {@code config/onemapapi.json}.
     *
     * @return The API key used to authenticate requests to the OneMap API.
     * @throws IOException If there is an error while reading the configuration file.
     */
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

    /**
     * Retrieves the geographic coordinates for a given postal code by querying the OneMap API
     * using the provided postal code.
     *
     * @param postalCode The postal code for which to retrieve the coordinates.
     * @return A string containing the coordinates of the location.
     * @throws IOException If an error occurs while making the API request or processing the response.
     */
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

    /**
     * Calculates the estimated driving time between two coordinates using the OneMap routing API and
     * returns a {@link LocationDTO} object containing the calculated driving time (in minutes) and distance (in kilometers).</p>
     *
     * @param startCoordinates The starting coordinates.
     * @param endCoordinates The destination coordinates.
     * @return A {@link LocationDTO} object containing the estimated driving time (in minutes) and distance (in kilometers).
     * @throws IOException If an error occurs while making the API request or processing the response.
     */
    @Override
    public LocationDTO calculateDrivingTime(String startCoordinates, String endCoordinates) throws IOException {
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

    /**
     * Builds the URL for the routing API request using the provided start and end coordinates.
     *
     * @param startCoordinates The starting coordinates.
     * @param endCoordinates The destination coordinates.
     * @return A URL string to query the routing API.
     */
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

    /**
     * Extracts the coordinates from the JSON response returned by the OneMap API.
     *
     * @param response The JSON response from the OneMap API containing the location results or null if no results are found.
     * @return A string containing the latitude and longitude.
     * @throws JsonProcessingException If there is an error processing the JSON response.
     */
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

    /**
     * Extracts the driving time and distance from the JSON response returned by the OneMap routing API.
     *
     * @param response The JSON response from the OneMap routing API containing route details.
     * @return A {@link LocationDTO} object containing the driving time (in minutes) and distance (in kilometers) or null if
     * no route is found.
     * @throws IOException If there is an error processing the JSON response.
     */
    private LocationDTO extractDrivingTime(String response) throws IOException {
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
            double totalDistanceInMeters = routeSummary.path("total_distance").asInt();

            // Convert from M to Km
            double totalDistanceInKm = totalDistanceInMeters/1000;

            // Return the driving time in minutes
            return new LocationDTO(totalTimeInMinutes, totalDistanceInKm);
        } else {
            return null;  // No route found
        }
    }
}

