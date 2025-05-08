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
    private final MainViewModel mainViewModel;

    public TourLogViewModel(MainViewModel mainViewModel) {

        this.selectedTour = mainViewModel.getSelectedTour();
        this.mainViewModel = mainViewModel;
    }

    public ObservableList<TourLog> getLogs() {
        Tour tour = selectedTour.get();
        return tour != null ? tour.getObservableLogs() : FXCollections.emptyObservableList();
    }

    public void deleteLog(TourLog tourLog) {
        Tour tour = selectedTour.get();
        if (tour != null) {
            ObservableList<TourLog> logs = tour.getObservableLogs();
            logs.remove(tourLog);
        }
    }

    public void editLog(TourLog tourLog) {
        mainViewModel.setSelectedLog(tourLog);
    }

}
