package com.peaslimited.shoppeas.service;

import java.util.concurrent.ExecutionException;

public interface OneMapService {
    // Get coordinates from postal code
    String getCoordinates(String postalCode) throws InterruptedException, ExecutionException;
    // Calculate driving time between the user and wholesaler
    String calculateDrivingTime(String startCoordinates, String endCoordinates) throws InterruptedException, ExecutionException;
}
