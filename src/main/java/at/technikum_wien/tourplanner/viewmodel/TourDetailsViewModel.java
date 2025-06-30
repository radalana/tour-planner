package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourUpdateDTO;
import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.utils.TimeConverter;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public class TourDetailsViewModel {
    private final MainViewModel mainViewModelViewModel;
    private final TourService tourService;

    public StringProperty nameProperty() {return mainViewModelViewModel.getSelectedTour().get().tourNameProperty();}
    public StringProperty descriptionProperty() {return mainViewModelViewModel.getSelectedTour().get().descriptionProperty();}
    public StringProperty fromProperty() {return mainViewModelViewModel.getSelectedTour().get().fromProperty();}
    public StringProperty toProperty() {return mainViewModelViewModel.getSelectedTour().get().toProperty();}
    public DoubleProperty distanceProperty() {return mainViewModelViewModel.getSelectedTour().get().distanceProperty();}
    public StringProperty transportTypeProperty() { return mainViewModelViewModel.getSelectedTour().get().transportTypeProperty(); }
    public StringProperty estimatedTimeProperty() {return mainViewModelViewModel.getSelectedTour().get().estimatedTimeProperty();}

    private final BooleanProperty validForm = new SimpleBooleanProperty(true);
    public BooleanProperty validFormProperty() {
        return validForm;
    }
    public TourDetailsViewModel(MainViewModel mainViewModelViewModel, TourService tourService) {
        this.mainViewModelViewModel = mainViewModelViewModel;
        this.tourService = tourService;
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
                    System.out.println("Failed to delete tour");
                }
            });
        }

    }

    public void openLogs() {
        mainViewModelViewModel.openTourLogsView();
    }

    public CompletableFuture<Void> updateTour() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();

        if (selected == null) {
            return CompletableFuture.completedFuture(null);
        }

        TourUpdateDTO tourUpdateDTO = new TourUpdateDTO(
                selected.getTourName(),
                selected.getDescription(),
                selected.getFrom(),
                selected.getTo(),
                selected.getTransportType()
        );

        return tourService.updateTourAsync(tourUpdateDTO, selected.getId())
                .thenAccept(editedTourData -> {
                    Platform.runLater(() -> {
                        selected.setId(editedTourData.getId());
                        selected.setTourName(editedTourData.getTourName());
                        selected.setDescription(editedTourData.getDescription());
                        selected.setTransportType(editedTourData.getTransportType());
                        selected.setFrom(editedTourData.getFromLocation());
                        selected.setTo(editedTourData.getToLocation());
                        selected.setDistance(editedTourData.getDistance());
                        selected.setEstimatedTime(TimeConverter.fromLongToString(editedTourData.getEstimatedTime()));
                    });
                });
    }

    public Long getTourId() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        return (selected != null) ? selected.getId() : null;
    }

    public List<String> fetchLocationSuggestions(String input) {
        return tourService.fetchLocationSuggestions(input);
    }

    public void validate() {
        boolean valid =
                nameProperty().get() != null && !nameProperty().get().isBlank() &&
                        descriptionProperty().get() != null && !descriptionProperty().get().isBlank() &&
                        fromProperty().get() != null && !fromProperty().get().isBlank() &&
                        toProperty().get() != null && !toProperty().get().isBlank() &&
                        transportTypeProperty().get() != null && !transportTypeProperty().get().isBlank();

        validForm.set(valid);
    }
}
