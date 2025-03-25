package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.collections.ObservableList;

public class TourTableViewModel {
    private final HomepageMediator homepageMediatorViewModel;

    public TourTableViewModel(HomepageMediator homepageMediatorViewModel) {
        this.homepageMediatorViewModel = homepageMediatorViewModel;
    }
    public ObservableList<Tour> getTours() {
        return homepageMediatorViewModel.getTours();
    }
    //устианавливает новое значение
    public void selectTour(Tour tour) {
        homepageMediatorViewModel.selectedTourProperty().set(tour);
    }
}
