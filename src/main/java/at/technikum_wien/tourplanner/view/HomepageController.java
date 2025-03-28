package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HomepageMediator;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class HomepageController {
    private final HomepageMediator homepageMediatorViewModel;
    //Elements
    @FXML private AnchorPane overlayPane;
    public HomepageController(HomepageMediator homepageViewModel) {
        this.homepageMediatorViewModel = homepageViewModel;

    }

    @FXML public void initialize() {
        overlayPane.visibleProperty().bindBidirectional(homepageMediatorViewModel.isNewTourFormOpenedProperty());
    }

    @FXML private void showPopupNewTour(ActionEvent actionEvent) {
        overlayPane.setVisible(true);
    }

}