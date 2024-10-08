package com.peaslimited.shoppeas.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface CurrencyService {
    double exchangeRate(double price, String preferredCurrency) throws IOException, URISyntaxException;
}
