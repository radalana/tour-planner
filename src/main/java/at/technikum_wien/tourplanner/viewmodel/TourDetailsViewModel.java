package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.beans.property.*;


public class TourDetailsViewModel {
    private final MainViewModel mainViewModelViewModel;

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

    public TourDetailsViewModel(MainViewModel mainViewModelViewModel) {
        this.mainViewModelViewModel = mainViewModelViewModel;
    }
    //is it data-binding
    public void loadTourData() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        if (selected != null) {
            name.set(selected.getName());
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
            mainViewModelViewModel.removeTour(selected);
        }

    }

    public void openLogs() {
        mainViewModelViewModel.openTourLogsView();
    }

    public void updateTour() {
        Tour selected = mainViewModelViewModel.getSelectedTour().get();
        //TODO validaton!

        if (selected != null) {
            selected.setName(name.get());
            selected.setDescription(description.get());
            selected.setFrom(from.get());
            selected.setTo(to.get());
            selected.setDistance(distance.get());
            selected.setEstimatedTime(estimatedTime.get());
        }
    }


}
