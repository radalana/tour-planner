package at.technikum_wien.tourplanner.httpClient;

import at.technikum_wien.tourplanner.dto.TourLogDTO;
import at.technikum_wien.tourplanner.model.TourLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class TourLogService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/tours";

    public TourLogService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

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
}
