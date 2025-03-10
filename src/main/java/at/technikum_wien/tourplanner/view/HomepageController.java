package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;
import javafx.fxml.FXML;

public class HomepageController {
    @FXML private TourTableController tourTableController;

    private final HomepageViewModel homepageViewModel;
    public HomepageController(HomepageViewModel homepageViewModel) {
        this.homepageViewModel = homepageViewModel;
    }

    @FXML public void initialize() {

    }

}