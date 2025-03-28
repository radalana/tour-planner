package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.collections.ObservableList;

public class TourTableViewModel {
    private final MainViewModel mainViewModelViewModel;

    public TourTableViewModel(MainViewModel mainViewModelViewModel) {
        this.mainViewModelViewModel = mainViewModelViewModel;
    }
    public ObservableList<Tour> getTours() {
        return mainViewModelViewModel.getTours();
    }
    //set new selceted Tour
    public void selectTour(Tour tour) {
        mainViewModelViewModel.selectedTourProperty().set(tour);
    }
}
