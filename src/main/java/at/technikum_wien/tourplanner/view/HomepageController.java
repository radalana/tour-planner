package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class HomepageController {
    private final MainViewModel mainViewModelViewModel;
    //Elements
    @FXML private AnchorPane overlayPane;
    public HomepageController(MainViewModel homepageViewModel) {
        this.mainViewModelViewModel = homepageViewModel;

    }

    @FXML public void initialize() {
        overlayPane.visibleProperty().bindBidirectional(mainViewModelViewModel.isNewTourFormOpenedProperty());
    }

    @FXML private void showPopupNewTour(ActionEvent actionEvent) {
        overlayPane.setVisible(true);
    }

}