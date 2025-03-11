package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import javafx.fxml.FXML;

public class NewTourController {
    private final NewTourViewModel newTourViewModel;

    public NewTourController(NewTourViewModel newTourViewModel) {
        this.newTourViewModel = newTourViewModel;
    }

    @FXML public void initialize() {

    }
}
