package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.LocationDTO;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This interface provides functionalities such as retrieving coordinates from a postal code and calculating the driving time between
 * two sets of coordinates (consumer and wholesaler locations) via the OneMap API.
 */
public interface OneMapService {
    // Get coordinates from postal code
    /**
     * Retrieves the geographic coordinates for a given postal code by querying the OneMap API
     * using the provided postal code.
     *
     * @param postalCode The postal code for which to retrieve the coordinates.
     * @return A string containing the coordinates of the location.
     * @throws IOException If an error occurs while making the API request or processing the response.
     */
    String getCoordinates(String postalCode) throws InterruptedException, ExecutionException, IOException;

    // Calculate driving time between the user and wholesaler
    /**
     * Calculates the estimated driving time between two coordinates using the OneMap routing API and
     * returns a {@link LocationDTO} object containing the calculated driving time (in minutes) and distance (in kilometers).</p>
     *
     * @param startCoordinates The starting coordinates.
     * @param endCoordinates The destination coordinates.
     * @return A {@link LocationDTO} object containing the estimated driving time (in minutes) and distance (in kilometers).
     * @throws IOException If an error occurs while making the API request or processing the response.
     */
    LocationDTO calculateDrivingTime(String startCoordinates, String endCoordinates) throws InterruptedException, ExecutionException, IOException;

}
