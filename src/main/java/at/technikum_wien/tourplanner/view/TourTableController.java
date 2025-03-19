package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TourTableController {
    //references used to setup data-binding
    @FXML private TableView<Tour> tourTableView;
    @FXML private TableColumn<Tour,String> tourNameColumn;
    /*
    @FXML private TableColumn transportTypeColumn;
    @FXML private TableColumn fromColumn;
    @FXML private TableColumn toColumn;
    @FXML private TableColumn distanceColumn;
    @FXML private TableColumn estimatedTimeColumn;

     */

    private final TourTableViewModel tourTableViewModel;
    public TourTableController(TourTableViewModel tourTableViewModel) {
        this.tourTableViewModel = tourTableViewModel;
    }
    @FXML
    public void initialize() {

        //get list of tours, subscription
        tourTableView.setItems(tourTableViewModel.getTours());
        //adjust column widths
        tourTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //how/what to show in each column
        tourNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //debug
        tourTableView.getItems().addListener((ListChangeListener<Tour>) change -> {
            System.out.println("Table item count changed: " + tourTableView.getItems().size());
        });
    }
}
