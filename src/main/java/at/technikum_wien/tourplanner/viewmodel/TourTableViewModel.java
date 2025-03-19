package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppMediatorViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourTableViewModel {
    private final AppMediatorViewModel mediatorViewModel;
    public TourTableViewModel(AppMediatorViewModel mediatorViewModel) {
        this.mediatorViewModel = mediatorViewModel;
    }
}
