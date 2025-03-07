package at.technikum_wien.tourplanner.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomepageController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}