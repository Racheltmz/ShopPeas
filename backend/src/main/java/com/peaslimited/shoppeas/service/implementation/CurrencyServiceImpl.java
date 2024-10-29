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

@Service
public class CurrencyServiceImpl implements CurrencyService {

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


