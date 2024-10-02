package com.peaslimited.shoppeas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    // Configuration for CORS to allow GET, POST, PATCH, DELETE HTTP methods from another server
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        // Allow all origins
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        // Allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Support user credentials to expose response to frontend JavaScript code
        // Ensures that 'Access-Control-Allow-Origin' header is not a wildcard
        configuration.setAllowCredentials(true);
        // Use URL path patterns to select CorsConfiguration for a request
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}