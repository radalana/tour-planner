package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppMediatorViewModel;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NewTourViewModel {
    private final AppMediatorViewModel mediatorViewModel;

    //values from form
    private final StringProperty tourName = new SimpleStringProperty();
    private final StringProperty tourDescription = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();

    public NewTourViewModel(AppMediatorViewModel mediatorViewModel) {
        this.mediatorViewModel = mediatorViewModel;
    }
    public StringProperty tourNameProperty() { return tourName; }
    public StringProperty descriptionProperty() { return tourDescription; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }

    public boolean saveTour() {
        //if (!valid) false


        //create
        Tour tour = new Tour(tourName.get());
        System.out.println("Tour object created: " + tour);
        mediatorViewModel.addTour(tour);

        //clean form
        tourName.set("");
        return true;
    }
}
