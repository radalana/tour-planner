package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourUpdateDTO;
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
    private final StringProperty transportType = new SimpleStringProperty();
    private final DoubleProperty estimatedTime = new SimpleDoubleProperty();

    public StringProperty nameProperty() {return name;}
    public StringProperty descriptionProperty() {return description;}
    public StringProperty fromProperty() {return from;}
    public StringProperty toProperty() {return to;}
    public DoubleProperty distanceProperty() {return distance;}
    public DoubleProperty estimatedTimeProperty() {return estimatedTime;}

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
        //TODO server sends timestamp, tourDTO doesn have this field --- error!
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        System.out.println("Selected tour before: " + selected);
        //TODO validaton!
        if (selected != null) {
            //TODO add transport type in edit field @Gabriela
            TourUpdateDTO tourUpdateDTO = new TourUpdateDTO(
                    name.get(),
                    description.get(),
                    from.get(),
                    to.get(),
                    transportType.get(),
                    distance.get(),
                    estimatedTime.get());
            tourService.updateTourAsync(tourUpdateDTO, selected.getId()).thenAccept(editedTourData -> {
                Platform.runLater(() -> {
                    selected.setId(editedTourData.getId());
                    selected.setTourName(editedTourData.getTourName());
                    selected.setDescription(editedTourData.getDescription());
                    selected.setEstimatedTime(editedTourData.getEstimatedTime());
                    selected.setFrom(editedTourData.getFromLocation());
                    selected.setTo(editedTourData.getToLocation());
                    selected.setDistance(editedTourData.getDistance());
                });
            }).exceptionally(ex -> {
                ex.printStackTrace();
                //show allert?
                return null;
            });
        }


    }
}
