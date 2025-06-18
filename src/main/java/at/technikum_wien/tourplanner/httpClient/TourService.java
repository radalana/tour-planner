package at.technikum_wien.tourplanner.httpClient;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.dto.TourUpdateDTO;
import at.technikum_wien.tourplanner.model.Tour;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

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

public class TourService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/tours";

    public TourService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<List<TourDTO>> fetchAllToursAsync() {
        //create GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();
        //Async
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        System.out.println("[TourService] Response status: " + response.statusCode());
                        System.out.println("[TourService] Response body: " + response.body());
                        return Arrays.asList(objectMapper.readValue(response.body(), TourDTO[].class));
                    }catch(IOException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(ex -> {
            System.err.println("[TourService] Request failed: " + ex.getMessage());
            ex.printStackTrace();
            return List.of();
        });
    }

    public CompletableFuture<Tour> createTourAsync(Tour tour) {
        try{
            String body = objectMapper.writeValueAsString(tour.toDTO()); //object -> json
            System.out.println("[TourService createTourAsync(Tour tour)] Request body: " + body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
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
                .thenApply(response -> {
                    int statusCode = response.statusCode();
                    System.out.println("[TourService deleteTourAsync] Response status: " + statusCode);
                    return statusCode == 204;
                });
    }

    public CompletableFuture<TourDTO> updateTourAsync(TourUpdateDTO editedData, Long id) {
        String url = baseUrl + "/" + id;
        try{
            String body = objectMapper.writeValueAsString(editedData);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        System.out.println("[TourService UPDATE] Response status: " + response.statusCode());
                        System.out.println("[TourService UPDATE] Response body: " + response.body());
                        return objectMapper.readValue(response.body(), TourDTO.class);
                    }catch(IOException e){
                        throw new RuntimeException(e);
                    }
                });
        }catch (JsonProcessingException e){
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
                        System.out.println("[TourService search] Response status: " + response.statusCode());
                        System.out.println("[TourService search] Response body: " + response.body());
                        return Arrays.asList(objectMapper.readValue(response.body(), TourDTO[].class));
                    }catch(IOException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("[TourService] Request failed: " + ex.getMessage());
                    ex.printStackTrace();
                    return List.of();
                });

    }

    public CompletableFuture<JSONObject> getRouteInfo(String from, String to) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = "http://localhost:8080/api/route?from=" + URLEncoder.encode(from, StandardCharsets.UTF_8)
                        + "&to=" + URLEncoder.encode(to, StandardCharsets.UTF_8);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                JSONObject json = new JSONObject(response.body());

                JSONObject summary = json.getJSONArray("features")
                        .getJSONObject(0)
                        .getJSONObject("properties")
                        .getJSONArray("segments")
                        .getJSONObject(0);

                JSONObject result = new JSONObject();
                result.put("distance", summary.getDouble("distance"));
                result.put("duration", summary.getDouble("duration"));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }


    /* idk, maybe will be needed later, user1 change tour, user2 have actuel data
    public CompletableFuture<Tour> fetchTourByIdAsync(Tour tour) {
        String url = baseUrl + "/" + tour.getId();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try{
                        return objectMapper.readValue(response.body(), Tour.class);
                    }catch (IOException e){
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });
    }
     */
}
