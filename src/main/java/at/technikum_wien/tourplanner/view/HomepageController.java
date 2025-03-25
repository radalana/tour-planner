package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.Mediator;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class HomepageController {

    //Controllers
    @FXML private TourTableController tourTableController;
    @FXML private NewTourController newTourController;//injected controller of newTour.fxml

    //Elements
    @FXML private AnchorPane rootLayout;
    @FXML private AnchorPane overlayPane;

    private final Mediator homepageViewModel;
    public HomepageController(Mediator homepageViewModel) {
        this.homepageViewModel = homepageViewModel;

    }

    @FXML public void initialize() {

    }

    @FXML private void showPopupNewTour(ActionEvent actionEvent) {
        newTourController.getNewTourContainer().setVisible(true);
        overlayPane.setVisible(true);
        //TODO not clickable wenn popup
        //TODO darker
    }
}