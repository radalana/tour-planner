package at.technikum_wien.tourplanner.httpClient;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.dto.TourUpdateDTO;
import at.technikum_wien.tourplanner.model.Tour;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class TourService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/tours";
    private final String openRouteServiceKey;

    public TourService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.openRouteServiceKey = loadApiKeyFromConfig();
    }

    private String loadApiKeyFromConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("openrouteservice.api_key");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API key from config.properties", e);
        }
    }

    public CompletableFuture<List<TourDTO>> fetchAllToursAsync() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return Arrays.asList(objectMapper.readValue(response.body(), TourDTO[].class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return List.of();
                });
    }

    public CompletableFuture<Tour> createTourAsync(Tour tour) {
        return createTourAsync(tour.toDTO());
    }
    public CompletableFuture<Tour> createTourAsync(TourDTO data) {
        try {
            String body = objectMapper.writeValueAsString(data);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        System.out.println("Created tour response : " + response.body());
                        try {
                            TourDTO tourDTO = objectMapper.readValue(response.body(), TourDTO.class);
                            return Tour.fromDTO(tourDTO);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    public CompletableFuture<Boolean> deleteTourAsync(Tour tour) {
        String url = baseUrl + "/" + tour.getId();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> response.statusCode() == 204);
    }

    public CompletableFuture<TourDTO> updateTourAsync(TourUpdateDTO editedData, Long id) {
        String url = baseUrl + "/" + id;
        try {
            String body = objectMapper.writeValueAsString(editedData);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            System.out.println("Updating tour request : " + body);
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        System.out.println("Updating tour response : " + response.body());
                        if (response.statusCode() >= 400) {
                            throw new RuntimeException("Server returned error: " + response.body());
                        }
                        try {
                            return objectMapper.readValue(response.body(), TourDTO.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<List<TourDTO>> searchToursAsync(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = baseUrl + "/search?query=" + encodedQuery;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return Arrays.asList(objectMapper.readValue(response.body(), TourDTO[].class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return List.of();
                });
    }

    public List<String> fetchLocationSuggestions(String input) {
        try {
            String url = "https://api.openrouteservice.org/geocode/search?api_key=" +
                    URLEncoder.encode(openRouteServiceKey, StandardCharsets.UTF_8) +
                    "&text=" + URLEncoder.encode(input, StandardCharsets.UTF_8) +
                    "&size=5";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("fetchLocationSuggestions: " + response.body());
            JSONObject json = new JSONObject(response.body());

            JSONArray features = json.getJSONArray("features");
            List<String> suggestions = new ArrayList<>();
            for (int i = 0; i < features.length(); i++) {
                JSONObject props = features.getJSONObject(i).getJSONObject("properties");
                String label = props.getString("label");
                String layer = props.optString("layer", "unknown");
                suggestions.add(label + " (" + layer + ")");
            }
            return suggestions;

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
