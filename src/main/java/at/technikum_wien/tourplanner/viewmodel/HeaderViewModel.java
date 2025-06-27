package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.application.Platform;

import java.util.List;
import java.util.stream.Collectors;

public class HeaderViewModel {
    private final TourService tourService;
    private final MainViewModel mainViewModel;


    public HeaderViewModel(MainViewModel mainViewModel, TourService tourService) {
        this.tourService = tourService;
        this.mainViewModel = mainViewModel;
    }

    public void searchTours(String query) {
        tourService.searchToursAsync(query).thenAccept(tourDTOs -> {
            System.out.println("Synch tours search");
            List<Tour> tours = tourDTOs.stream()
                    .map(Tour::fromDTO)
                    .collect(Collectors.toList());
            Platform.runLater(() -> {
                mainViewModel.getTours().setAll(tours);
            });

        });
    }
}
