package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourLogService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class TourLogViewModel {
    //TODO make local selectedTour
    private final ObjectProperty<Tour> selectedTour;
    private final MainViewModel mainViewModel;
    private final TourLogService tourLogService;

    public TourLogViewModel(MainViewModel mainViewModel, TourLogService tourLogService) {
        this.selectedTour = mainViewModel.getSelectedTour();
        this.mainViewModel = mainViewModel;
        this.tourLogService = tourLogService;
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
    public void syncLogs() {
        if (selectedTour.get() != null) {
            tourLogService.fetchLogsByTourIdAsync(selectedTour.get().getId()).thenAccept(logs -> {
                System.out.println("Sychronous logs");
                Platform.runLater(() -> {
                    System.out.println("Fetched " + logs.size() + " logs");
                    selectedTour.get().getObservableLogs().setAll(logs);
                });
            });
        }else{
            System.out.println("No tour selected");
        }
    }
}
