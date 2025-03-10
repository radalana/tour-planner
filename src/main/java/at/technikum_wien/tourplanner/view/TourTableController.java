package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TourTableController {
    //references used to setup data-binding
    //TODO private or public
    public TableView tableView;
    public TableColumn tourNameColumn;
    public TableColumn transportTypeColumn;
    public TableColumn fromColumn;
    public TableColumn toColumn;
    public TableColumn distanceColumn;
    public TableColumn estimatedTimeColumn;




    private final TourTableViewModel tourTableViewModel;
    public TourTableController(TourTableViewModel tourTableViewModel) {
        this.tourTableViewModel = tourTableViewModel;
    }
    @FXML
    public void initialize() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
