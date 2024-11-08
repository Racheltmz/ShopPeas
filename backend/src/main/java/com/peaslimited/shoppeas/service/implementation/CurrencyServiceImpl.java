package com.peaslimited.shoppeas.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peaslimited.shoppeas.service.CurrencyService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * This class is an implementation of the {@link CurrencyService} interface
 * that provides methods for retrieving currency exchange rates and converting prices on the wholesaler's end
 * based on the exchange rates and their preferred currency.
 * It retrieves the API key from a configuration file (config/currencyapi.json) and uses an external
 * API (https://currencyapi.com/) to fetch the current exchange rates.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    /**
     * Loads the API key from a configuration file located on the classpath "config/currencyapi.json".
     *
     * @return The API key used to authenticate requests to the external currency API.
     * @throws IOException If an error occurs while reading the configuration file.
     */
    @PostConstruct
    public String getApiKeyPath() throws IOException {
        // Load resource from classpath
        ClassPathResource resource = new ClassPathResource("config/currencyapi.json");

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
     * Retrieves the exchange rate for a specified preferred currency (from SGD) and converts it to
     * a given preferred currency.
     *
     * @param price The price to be converted from SGD to the preferred currency.
     * @param preferredCurrency The currency to convert the price to (e.g., "MYR").
     * @return The converted price in the preferred currency, based on the latest exchange rate.
     * @throws IOException If an error occurs while querying the external currency API or processing the response.
     * @throws URISyntaxException If the constructed URL for the API request is invalid.
     */
    @Override
    public double exchangeRate(double price, String preferredCurrency) throws IOException, URISyntaxException {
        String route = String.format("https://api.currencyapi.com/v3/latest?apikey=%s&base_currency=SGD&currencies=%s", getApiKeyPath(), preferredCurrency);

        URL url = new URI(route).toURL();
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Initialise converted value and mapper
        double exchangeRate = 0.0;
        ObjectMapper objectMapper = new ObjectMapper();

        // Get converted value
        try (InputStream responseStream = request.getInputStream()) {
            // Read the JSON response into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(responseStream);

            // Extract the "result" field
            exchangeRate = Double.parseDouble(jsonNode.get("data").get("MYR").get("value").toString());
        }

        return exchangeRate * price;
    }
}


