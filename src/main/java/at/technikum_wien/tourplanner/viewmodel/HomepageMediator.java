package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
@Getter
public class HomepageMediator {
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();//for delete/modify/details
    private final BooleanProperty isNewTourFormOpened = new SimpleBooleanProperty(false);


    public HomepageMediator() {
        tours.addAll(new Tour("Vienna Bratislava", "Weekend in Bratislaba", "Vienna", "Bratislaba", "Train", 40.0, "1 hour", "bla-bla"),
                new Tour("Trip in Sibirien", "Urlaub in Russland", "Wien", "Ulan-Use", "Plain", 9000.0, "2 Days", "bla-bla"));
    }
    public ObjectProperty<Tour> selectedTourProperty() {return selectedTour;}
    public BooleanProperty isNewTourFormOpenedProperty() {return isNewTourFormOpened;}
    public void addTour(Tour tour) {
        tours.add(tour);
    }

}
