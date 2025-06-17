package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HeaderViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HeaderController {
    private final HeaderViewModel headerViewModel;
    @FXML
    public TextField searchTextField;
    @FXML
    private AnchorPane headerPane;

    public HeaderController(HeaderViewModel headerViewModel) {
        this.headerViewModel = headerViewModel;
    }
    @FXML
    public void initialize() {
        //initial focus is set away from searchfield
        Platform.runLater(() -> headerPane.requestFocus());
    }
    @FXML
    private void generateSummaryReport() {
        // TODO: replace with actual report generation
    }

    @FXML
    private void handleSearch() {
        String query = searchTextField.getText(); // ← просто берём текст
        headerViewModel.searchTours(query);
    }

}