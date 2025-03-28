package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.FXMLDependencyInjection;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

@Getter
public class HomepageMediator {
    //Tour
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
    public void removeTour(Tour tour) {
        tours.remove(tour);
    }

    public void openTourLogsView() {
        try {
            Parent root = FXMLDependencyInjection.load("logs.fxml");
            Stage stage = new Stage();
            stage.setTitle("Logs");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
