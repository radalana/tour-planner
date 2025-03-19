package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppMediatorViewModel;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourTableViewModel {
    private final AppMediatorViewModel mediatorViewModel;
    public TourTableViewModel(AppMediatorViewModel mediatorViewModel) {
        this.mediatorViewModel = mediatorViewModel;
    }

    //кто и когда вызывает
    public ObservableList<Tour> getTours() {
        return mediatorViewModel.getTours();
    }
}
