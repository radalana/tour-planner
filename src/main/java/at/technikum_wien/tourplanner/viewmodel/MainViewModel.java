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
public class MainViewModel {
    //Tour
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();//for delete/modify/details
    private final ObjectProperty<TourLog> selectedLog = new SimpleObjectProperty<>();//for edit

    private final BooleanProperty isNewTourFormOpened = new SimpleBooleanProperty(false);

    public MainViewModel() {

    }
    public ObjectProperty<TourLog> selectedLogProperty() { return selectedLog; }
    public TourLog getSelectedLog() { return selectedLog.get(); }
    public void setSelectedLog(TourLog log) { this.selectedLog.set(log); }
    public ObjectProperty<Tour> selectedTourProperty() {return selectedTour;}
    public BooleanProperty isNewTourFormOpenedProperty() {return isNewTourFormOpened;}

    public void addTour(Tour tour) {
        tours.add(tour);
    }
    public void removeTour(Tour tour) {
        tours.remove(tour);
    }
    //maybe extract
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
