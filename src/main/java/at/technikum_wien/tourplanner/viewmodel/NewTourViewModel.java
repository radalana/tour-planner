package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppEventAggregator;
import at.technikum_wien.tourplanner.TourAddedEvent;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NewTourViewModel {
    private final AppEventAggregator mediatorViewModel;

    //values from form
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty estTime = new SimpleStringProperty();
    private final StringProperty routInfo = new SimpleStringProperty();

    public NewTourViewModel(AppEventAggregator mediatorViewModel) {
        this.mediatorViewModel = mediatorViewModel;
    }
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public DoubleProperty distanceProperty() { return distance; }
    public StringProperty estTimeProperty() { return estTime; }
    public StringProperty routInfoProperty() { return routInfo; }

    public boolean saveTour() {
        //if (!valid) false


        //create
        Tour tour = new Tour(
                name.get(),
                description.get(),
                from.get(),
                to.get(),
                transportType.get(),
                distance.get(),
                estTime.get(),
                routInfo.get()
                );
        System.out.println("Tour object created: " + tour);
        mediatorViewModel.publish(new TourAddedEvent(tour));

        //clean form
        name.set("");
        return true;
    }
}
