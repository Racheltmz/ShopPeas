package com.peaslimited.shoppeas.service;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * This interface provides methods for retrieving currency exchange rates and converting prices on the wholesaler's end
 * based on the exchange rates and their preferred currency. It uses an external
 * API (https://currencyapi.com/) to fetch the current exchange rates.
 */
public interface CurrencyService {

    /**
     * Retrieves the exchange rate for a specified preferred currency (from SGD) and converts it to
     * a given preferred currency.
     *
     * @param price The price to be converted from SGD to the preferred currency.
     * @param preferredCurrency The currency to convert the price to (e.g., "MYR").
     * @return The converted price in the preferred currency, based on the latest exchange rate.
     * @throws IOException If an error occurs while querying the external currency API or processing the response.
     * @throws URISyntaxException If the constructed URL for the API request is invalid.
     */
    double exchangeRate(double price, String preferredCurrency) throws IOException, URISyntaxException;
}
