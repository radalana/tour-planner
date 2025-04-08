package at.technikum_wien.tourplanner.httpClient;

import at.technikum_wien.tourplanner.model.Tour;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TourService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl = "http://localhost:8080/api/tours";

    public TourService(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<List<Tour>> fetchAllToursAsync() {
        //create GET request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();
        //Async
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        return Arrays.asList(objectMapper.readValue(response.body(), Tour[].class));
                    }catch(IOException e){
                        throw new RuntimeException(e);
                    }
                });
    }
}
