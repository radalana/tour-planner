package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.httpClient.TourService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HeaderViewModel {
    private final TourService tourService;


    public HeaderViewModel(TourService tourService) {
        this.tourService = tourService;
    }

    public void searchTours(String query) {
        tourService.searchToursAsync(query);
    }
}
