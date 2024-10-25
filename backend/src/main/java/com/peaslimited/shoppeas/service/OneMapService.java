package com.peaslimited.shoppeas.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface OneMapService {
    // Get coordinates from postal code
    String getCoordinates(String postalCode) throws InterruptedException, ExecutionException, IOException;
    // Calculate driving time between the user and wholesaler
    String calculateDrivingTime(String startCoordinates, String endCoordinates) throws InterruptedException, ExecutionException, IOException;
}
