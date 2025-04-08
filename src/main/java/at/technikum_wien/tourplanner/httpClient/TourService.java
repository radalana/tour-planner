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
    private final String baseUrl = "https://c7e41eaf-9f72-4f0e-91a3-27a668b2dc9e.mock.pstmn.io/tours";

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
                        System.out.println("[TourService] Response status: " + response.statusCode());
                        System.out.println("[TourService] Response body: " + response.body());
                        return Arrays.asList(objectMapper.readValue(response.body(), Tour[].class));
                    }catch(IOException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .exceptionally(ex -> {
            System.err.println("[TourService] Request failed: " + ex.getMessage());
            ex.printStackTrace();
            return List.of(); // Возвращаем пустой список, чтобы цепочка продолжилась
        });
    }
}
