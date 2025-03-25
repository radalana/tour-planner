package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.collections.ObservableList;

public class TourTableViewModel {
    private final Mediator mediatorViewModel;

    public TourTableViewModel(Mediator mediatorViewModel) {
        this.mediatorViewModel = mediatorViewModel;
    }
    public ObservableList<Tour> getTours() {
        return mediatorViewModel.getTours();
    }
    //устианавливает новое значение
    public void selectTour(Tour tour) {
        mediatorViewModel.selectedTourProperty().set(tour);
    }
}
