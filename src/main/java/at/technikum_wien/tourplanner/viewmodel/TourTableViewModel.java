package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.collections.ObservableList;

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
    }
}
