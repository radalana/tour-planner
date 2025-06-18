package at.technikum_wien.tourplanner.httpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class TourLogService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/tours";

    public TourLogService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }
}
