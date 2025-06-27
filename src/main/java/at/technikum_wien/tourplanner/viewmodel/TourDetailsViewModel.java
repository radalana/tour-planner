package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourUpdateDTO;
import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.utils.TimeConverter;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.util.List;


public class TourDetailsViewModel {
    private final MainViewModel mainViewModelViewModel;
    private final TourService tourService;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final StringProperty estimatedTime = new SimpleStringProperty();

    public StringProperty nameProperty() {return name;}
    public StringProperty descriptionProperty() {return description;}
    public StringProperty fromProperty() {return from;}
    public StringProperty toProperty() {return to;}
    public DoubleProperty distanceProperty() {return distance;}
    public StringProperty transportTypeProperty() { return transportType; }
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
            transportType.set(selected.getTransportType());
            from.set(selected.getFrom());
            to.set(selected.getTo());
            distance.set(selected.getDistance());
            estimatedTime.set(selected.getEstimatedTime());
            updateRouteInfo();
        }
    }

    //call backend to get updated distance + estimated time
    public void updateRouteInfo() {
        String fromLocation = from.get();
        String toLocation = to.get();
        String transport = transportType.get();

        tourService.getRouteInfo(fromLocation, toLocation, transport).thenAccept(response -> {

            if (response != null) {
                double distanceKm = response.getDouble("distance") / 1000.0;
                double durationSec = response.getDouble("duration");
                //TODO check if model real changed not only variables
                String formattedTime = TimeConverter.fromLongToString((long )durationSec);
                Platform.runLater(() -> {
                    distance.set(distanceKm);
                    estimatedTime.set(formattedTime);
                });
            }
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
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

    public void updateTour() {
        //TODO server sends timestamp, tourDTO doesn have this field --- error!
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        System.out.println("Selected tour before: " + selected);

        if (selected != null) {
            TourUpdateDTO tourUpdateDTO = new TourUpdateDTO(
                    name.get(),
                    description.get(),
                    from.get(),
                    to.get(),
                    transportType.get(),
                    distance.get());
            tourService.updateTourAsync(tourUpdateDTO, selected.getId()).thenAccept(editedTourData -> {
                Platform.runLater(() -> {
                    selected.setId(editedTourData.getId());
                    selected.setTourName(editedTourData.getTourName());
                    selected.setDescription(editedTourData.getDescription());
                    selected.setTransportType(editedTourData.getTransportType());
                    selected.setFrom(editedTourData.getFromLocation());
                    selected.setTo(editedTourData.getToLocation());
                    selected.setDistance(editedTourData.getDistance());

                    updateRouteInfo();
                });
            }).exceptionally(ex -> {
                ex.printStackTrace();
                //show allert?
                return null;
            });
        }

    }

    public Long getTourId() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        return (selected != null) ? selected.getId() : null;
    }

    public List<String> fetchLocationSuggestions(String input) {
        return tourService.fetchLocationSuggestions(input);
    }
}
