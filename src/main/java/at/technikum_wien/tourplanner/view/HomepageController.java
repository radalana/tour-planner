package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.FXMLDependencyInjection;
import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class HomepageController {
    @FXML private TourTableController tourTableController;

    private final HomepageViewModel homepageViewModel;
    public HomepageController(HomepageViewModel homepageViewModel) {
        this.homepageViewModel = homepageViewModel;
    }

    @FXML public void initialize() {

    }

    public void navigateToAddTour(ActionEvent actionEvent) {
        try{
            Parent addTourParent = FXMLDependencyInjection.load("newTour.fxml");
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}