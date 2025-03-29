package at.technikum_wien.tourplanner.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HeaderController {
    @FXML
    public TextField searchField;
    @FXML
    private AnchorPane headerPane;

    @FXML
    public void initialize() {
        //initial focus is set away from searchfield
        Platform.runLater(() -> headerPane.requestFocus());
    }
    @FXML
    private void generateSummaryReport() {
        // TODO: replace with actual report generation
    }

}