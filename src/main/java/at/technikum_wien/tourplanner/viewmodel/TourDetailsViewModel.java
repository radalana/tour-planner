package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.beans.property.*;


public class TourDetailsViewModel {
    private final HomepageMediator homepageMediatorViewModel;

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

    public TourDetailsViewModel(HomepageMediator homepageMediatorViewModel) {
        this.homepageMediatorViewModel = homepageMediatorViewModel;
    }

    public void loadTourData() {
        Tour selected = homepageMediatorViewModel.getSelectedTour().get();
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
        Tour selected = homepageMediatorViewModel.getSelectedTour().get();
        if (selected != null) {
            homepageMediatorViewModel.removeTour(selected);
        }

    }

    public void openLogs() {
        homepageMediatorViewModel.openTourLogsView();
    }


}
