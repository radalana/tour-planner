package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.utils.TimeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NewTourViewModel {
    private final MainViewModel mainViewModelViewModel;
    private final TourService tourService;

    // Values from form
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty estTime = new SimpleStringProperty();
    private final StringProperty routInfo = new SimpleStringProperty();

    private final BooleanProperty validForm = new SimpleBooleanProperty(true);
    private final BooleanProperty fromSelectedFromList = new SimpleBooleanProperty(false);
    private final BooleanProperty toSelectedFromList = new SimpleBooleanProperty(false);

    public NewTourViewModel(MainViewModel mainViewModelViewModel, TourService tourService) {
        this.mainViewModelViewModel = mainViewModelViewModel;
        this.tourService = tourService;
    }

    public BooleanProperty isNewTourContainerVisibleProperty() {
        return mainViewModelViewModel.getIsNewTourFormOpened();
    }

    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public DoubleProperty distanceProperty() { return distance; }
    public StringProperty estTimeProperty() { return estTime; }

    public BooleanProperty validFormProperty() { return validForm; }
    //check if user choosed real location
    public BooleanProperty fromSelectedFromListProperty() { return fromSelectedFromList; }
    public BooleanProperty toSelectedFromListProperty() { return toSelectedFromList; }

    // Input validation for required fields only
    public boolean validate() {
        if (name.get() == null || name.get().trim().isEmpty()) return false;
        if (description.get() == null || description.get().trim().isEmpty()) return false;
        if (from.get() == null || from.get().trim().isEmpty()) return false;
        if (to.get() == null || to.get().trim().isEmpty()) return false;
        if (transportType.get() == null || transportType.get().trim().isEmpty()) return false;
        return true;
    }

    public void validateForm() {
        boolean valid =
                nameProperty().get() != null && !nameProperty().get().isBlank() &&
                        descriptionProperty().get() != null && !descriptionProperty().get().isBlank() &&
                        fromProperty().get() != null && !fromProperty().get().isBlank() &&
                        toProperty().get() != null && !toProperty().get().isBlank() &&
                        transportTypeProperty().get() != null && !transportTypeProperty().get().isBlank() &&
                        fromSelectedFromList.get();

        validForm.set(valid);
    }

    public List<String> fetchLocationSuggestions(String input) {
        return tourService.fetchLocationSuggestions(input);
    }

    public boolean createTour() {
        if (!validate()) {
            return false;
        }
        Tour tour = new Tour(
                name.get(),
                description.get(),
                from.get(),
                to.get(),
                transportType.get()
        );
        tourService.createTourAsync(tour).thenAccept(addedTour -> {
            Platform.runLater(() -> {
                mainViewModelViewModel.addTour(addedTour);
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });

        // Clean form values
        name.set("");
        description.set("");
        from.set("");
        to.set("");
        transportType.set("");
        distance.set(0);
        estTime.set("");
        routInfo.set("");
        return true;
    }

    public void importTour(File file) {
        if (file != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                TourDTO importedTour = objectMapper.readValue(file, TourDTO.class);

                // Call backend to save tour
                tourService.addTourAsync(importedTour).thenAccept(savedDTO -> {
                    if (savedDTO != null) {
                        Platform.runLater(() -> {
                            Tour tour = Tour.fromDTO(savedDTO);
                            mainViewModelViewModel.addTour(tour);
                        });
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
