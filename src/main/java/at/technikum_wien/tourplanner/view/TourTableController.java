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
    @FXML private TableColumn<Tour,String> transportTypeColumn;
    @FXML private TableColumn<Tour,String> fromColumn;
    @FXML private TableColumn<Tour,String> toColumn;
    @FXML private TableColumn<Tour,Double> distanceColumn;
    @FXML private TableColumn<Tour,String> estimatedTimeColumn;

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
        transportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        estimatedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedTime"));
        //debug
        tourTableView.getItems().addListener((ListChangeListener<Tour>) change -> {
            System.out.println("Table item count changed: " + tourTableView.getItems().size());
        });
    }
}
