package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.httpClient.TourLogService;
import at.technikum_wien.tourplanner.httpClient.TourService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.net.http.HttpClient;

@Getter
public class ServiceProvider {
    private TourService tourService;
    private TourLogService tourLogService;
    public ServiceProvider() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = HttpClient.newHttpClient();
        this.tourService = new TourService(httpClient);
        this.tourLogService = new TourLogService(httpClient, objectMapper);
    }

}
