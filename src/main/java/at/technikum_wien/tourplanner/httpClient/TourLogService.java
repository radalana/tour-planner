package at.technikum_wien.tourplanner.httpClient;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.dto.TourLogDTO;
import at.technikum_wien.tourplanner.dto.TourLogUpdateDTO;
import at.technikum_wien.tourplanner.model.TourLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class TourLogService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/tours";

    public TourLogService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    //GET All
    public CompletableFuture<List<TourLog>> fetchLogsByTourIdAsync(Long tourId) {
        String url = baseUrl +"/" + tourId + "/logs";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try{
                        System.out.println("[TourLogService fetchAllToursByTourId] Response status: " + response.statusCode());
                        System.out.println("[TourLogService fetchAllToursByTourId] Response body: " + response.body());
                        List<TourLogDTO> data = objectMapper.readValue(
                                response.body(),
                                new TypeReference<List<TourLogDTO>>() {}
                        );
                        return data.stream()
                                .map(TourLog::fromDTO)
                                .collect(Collectors.toList());
                    }catch (JsonMappingException e) {
                        throw new RuntimeException("[ERROR RESPONSE GET/] JSON mapping error: " + e.getMessage());
                    }catch (JsonProcessingException e) {
                        throw new RuntimeException("[ERROR RESPONSE GET/] JSON processing error: " + e.getMessage());
                    }
                });

    }

    //POST
    public CompletableFuture<TourLog> createLogAsync(TourLog tourLog, Long tourId) throws NumberFormatException {
        String url = baseUrl +"/" + tourId + "/logs";
        TourLogDTO tourLogDTO = tourLog.toDTO();
        try{
            String body = objectMapper.writeValueAsString(tourLogDTO);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            System.out.println("[DEBUG create tour log] Request body: " + body);
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            System.out.println("[DEBUG] Response body: " + response.body());
                          TourLogDTO createdTourLogDTO = objectMapper.readValue(response.body(), TourLogDTO.class);
                          return TourLog.fromDTO(createdTourLogDTO);
                        }catch (JsonMappingException e) {
                            throw new RuntimeException("[ERROR RESPONSE] JSON mapping error: " + e.getMessage());
                        }catch (JsonProcessingException e) {
                            throw new RuntimeException("[ERROR RESPONSE] JSON processing error: " + e.getMessage());
                        }
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("[ERROR REQUEST] Failed to serialize TourLogDTO", e);
        }
    }

    public CompletableFuture<TourLog> updateLogAsync(TourLogUpdateDTO data, Long id, Long tourId) throws NumberFormatException {
        String url = baseUrl +"/" + tourId + "/logs/" + id;
        try {
            String body = objectMapper.writeValueAsString(data);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            System.out.println("[DEBUG update tour log] Request body: " + body);
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            TourLogDTO editedTourLog = objectMapper.readValue(response.body(), TourLogDTO.class);
                            System.out.println("[TourLogService UPDATE] Response status: " + response.statusCode());
                            System.out.println("[TourLogService UPDATE] Response body: " + response.body());
                            return TourLog.fromDTO(editedTourLog);
                        }catch (JsonMappingException e) {
                            throw new RuntimeException("[ERROR RESPONSE] JSON mapping error: " + e.getMessage());
                        }catch (JsonProcessingException e) {
                            throw new RuntimeException("[ERROR RESPONSE] JSON processing error: " + e.getMessage());
                        }
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("[ERROR REQUEST] Failed to serialize TourLogDTO by update", e);
        }
    }

    public CompletableFuture<Boolean> deleteLogAsync(Long id, Long tourId) {
        String url = baseUrl +"/" + tourId + "/logs/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> {
                    int statusCode = response.statusCode();
                    System.out.println("[DEBUG delete log] Response status: " + statusCode);
                    return statusCode == 204;
                });
    }

    public CompletableFuture<List<TourLogDTO>> getLogsAsync(Long tourId, String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = baseUrl +"/" + tourId + "/logs/search?query=" + encodedQuery;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                        .GET()
                        .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return Arrays.asList(objectMapper.readValue(response.body(), TourLogDTO[].class));
                    }catch (JsonProcessingException e) {
                        throw new RuntimeException("[ERROR RESPONSE] JSON mapping error: " + e.getMessage());
                    }
                });
    }
}
