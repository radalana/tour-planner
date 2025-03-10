package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TourTableController {
    //references used to setup data-binding
    @FXML private TableView tourTableView;
    @FXML private TableColumn tourNameColumn;
    @FXML private TableColumn transportTypeColumn;
    @FXML private TableColumn fromColumn;
    @FXML private TableColumn toColumn;
    @FXML private TableColumn distanceColumn;
    @FXML private TableColumn estimatedTimeColumn;




    private final TourTableViewModel tourTableViewModel;
    public TourTableController(TourTableViewModel tourTableViewModel) {
        this.tourTableViewModel = tourTableViewModel;
    }
    @FXML
    public void initialize() {
        System.out.println("TourTableController — initialize start");

        tourTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Tour> testData = FXCollections.observableArrayList(
                new Tour("Test Tour", "Car", "Vienna", "Graz", 200.0, "2h 30m")
        );
        tourTableView.setItems(testData);
        System.out.println("TourTableController — initialize end");
    }
}
