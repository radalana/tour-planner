package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class TourTableViewModel {
    private final MainViewModel mainViewModelViewModel;
    private final TourService tourService;

    public TourTableViewModel(MainViewModel mainViewModelViewModel, TourService tourService) {
        this.mainViewModelViewModel = mainViewModelViewModel;
        this.tourService = tourService;
    }
    public ObservableList<Tour> getTours() {
        return mainViewModelViewModel.getTours();
    }
    //set new selceted Tour
    public void selectTour(Tour tour) {
        mainViewModelViewModel.selectedTourProperty().set(tour);
    }

    public void syncTours() {
        tourService.fetchAllToursAsync().thenAccept(tourDTOList -> {
            System.out.println("Syncing tours");
            List<Tour> tours = tourDTOList.stream()
                    .map(Tour::fromDTO)
                    .collect(Collectors.toList());
            Platform.runLater(() -> {
                System.out.println("Fetched " + tours.size() + " tours");
                System.out.println(tours);
                mainViewModelViewModel.getTours().setAll(tours);
            });
        });

    }
}
