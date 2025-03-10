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
        tourTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
