package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.httpClient.TourService;
import lombok.Getter;

import java.net.http.HttpClient;

@Getter
public class ServiceProvider {
    private TourService tourService;
    public ServiceProvider() {
        HttpClient httpClient = HttpClient.newHttpClient();
        this.tourService = new TourService(httpClient);
    }

}
