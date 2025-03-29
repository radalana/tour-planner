package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class TourLogViewModel {
    private final ObjectProperty<Tour> selectedTour;

    public TourLogViewModel(MainViewModel mainViewModel) {
        this.selectedTour = mainViewModel.getSelectedTour();
    }

    public ObservableList<TourLog> getLogs() {
        Tour tour = selectedTour.get();
        return tour != null ? tour.getLogs() : FXCollections.emptyObservableList();
    }

}
