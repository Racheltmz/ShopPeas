package com.peaslimited.shoppeas.service;

import com.peaslimited.shoppeas.dto.LocationDTO;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface OneMapService {
    // Get coordinates from postal code
    String getCoordinates(String postalCode) throws InterruptedException, ExecutionException, IOException;

    // Calculate driving time between the user and wholesaler
    LocationDTO calculateDrivingTime(String startCoordinates, String endCoordinates) throws InterruptedException, ExecutionException, IOException;

}
