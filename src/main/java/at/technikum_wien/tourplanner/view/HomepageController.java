package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.FXMLDependencyInjection;
import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {
    @FXML private TourTableController tourTableController;
    @FXML private AnchorPane popupContainer;

    private final HomepageViewModel homepageViewModel;
    public HomepageController(HomepageViewModel homepageViewModel) {
        this.homepageViewModel = homepageViewModel;
    }

    @FXML public void initialize() {

    }

    @FXML private void navigateToAddTour(ActionEvent actionEvent) {
       popupContainer.setVisible(true);
    }
}