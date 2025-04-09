package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.beans.property.*;

public class NewTourViewModel {
    private final MainViewModel mainViewModelViewModel;

    //values from form
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty estTime = new SimpleStringProperty();
    private final StringProperty routInfo = new SimpleStringProperty();

    public NewTourViewModel(MainViewModel mainViewModelViewModel) {
        this.mainViewModelViewModel = mainViewModelViewModel;
    }
    public BooleanProperty isNewTourContainerVisibleProperty() {
        return mainViewModelViewModel.getIsNewTourFormOpened(); }

    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public DoubleProperty distanceProperty() { return distance; }
    public StringProperty estTimeProperty() { return estTime; }

    //input validation
    public boolean validate() {
        if (name.get() == null || name.get().trim().isEmpty()) return false;
        if (description.get() == null || description.get().trim().isEmpty()) return false;
        if (from.get() == null || from.get().trim().isEmpty()) return false;
        if (to.get() == null || to.get().trim().isEmpty()) return false;
        if (transportType.get() == null || transportType.get().trim().isEmpty()) return false;
        if (estTime.get() == null || estTime.get().trim().isEmpty()) return false;

        try {
            if (distance.get() <= 0) return false;
        } catch (Exception e) {
            return false;
        }

        try {
            double time = Double.parseDouble(estTime.get());
            if (time <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public void cancel() {
        //mediatorViewModel.closeNewTourOverlay();
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
                transportType.get(),
                distance.get(),
                estTime.get(),
                routInfo.get()
        );

        System.out.println("Tour object created: " + tour);
        mainViewModelViewModel.addTour(tour);

        // Clean the form
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
}