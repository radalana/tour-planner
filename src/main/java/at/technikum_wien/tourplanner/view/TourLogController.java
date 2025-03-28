package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.TourLogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TourLogController {
    private TourLogViewModel viewModel;
    public TourLogController(TourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @FXML private void initialize() {

    }
    public void handleAddLog(ActionEvent actionEvent) {

    }
}
