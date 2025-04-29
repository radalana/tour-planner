package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.application.Platform;
import javafx.beans.property.*;


public class TourDetailsViewModel {
    private final MainViewModel mainViewModelViewModel;
    private final TourService tourService;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty estimatedTime = new SimpleStringProperty();

    public StringProperty nameProperty() {return name;}
    public StringProperty descriptionProperty() {return description;}
    public StringProperty fromProperty() {return from;}
    public StringProperty toProperty() {return to;}
    public DoubleProperty distanceProperty() {return distance;}
    public StringProperty estimatedTimeProperty() {return estimatedTime;}

    public TourDetailsViewModel(MainViewModel mainViewModelViewModel, TourService tourService) {
        this.mainViewModelViewModel = mainViewModelViewModel;
        this.tourService = tourService;
    }
    //is it data-binding
    public void loadTourData() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        if (selected != null) {
            name.set(selected.getTourName());
            description.set(selected.getDescription());
            from.set(selected.getFrom());
            to.set(selected.getTo());
            distance.set(selected.getDistance());
            estimatedTime.set(selected.getEstimatedTime());
        }
    }

    public void deleteTour() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        if (selected != null) {
            tourService.deleteTourAsync(selected).thenAccept(succeed -> {
                if (succeed) {
                    Platform.runLater(() -> {
                        mainViewModelViewModel.removeTour(selected);
                    });
                }else {
                    //TODO Gabriela - notify tour not delete/not found etc.
                    System.out.println("Failed to delete tour");
                }
            });
        }

    }

    public void openLogs() {
        mainViewModelViewModel.openTourLogsView();
    }

    public void updateTour() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        //TODO validaton!

        if (selected != null) {
            selected.setTourName(name.get());
            selected.setDescription(description.get());
            selected.setFrom(from.get());
            selected.setTo(to.get());
            selected.setDistance(distance.get());
            selected.setEstimatedTime(estimatedTime.get());
        }
    }


}
