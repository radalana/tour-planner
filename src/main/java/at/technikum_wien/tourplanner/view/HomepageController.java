package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.Mediator;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class HomepageController {
    private final Mediator mediatorViewModel;
    //Elements
    @FXML private AnchorPane overlayPane;
    public HomepageController(Mediator homepageViewModel) {
        this.mediatorViewModel = homepageViewModel;

    }

    @FXML public void initialize() {
        overlayPane.visibleProperty().bindBidirectional(mediatorViewModel.isNewTourFormOpenedProperty());
    }

    @FXML private void showPopupNewTour(ActionEvent actionEvent) {
        overlayPane.setVisible(true);
    }
}