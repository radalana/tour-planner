package at.technikum_wien.tourplanner.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HeaderController {
    @FXML
    public TextField searchField;
    @FXML
    private AnchorPane headerPane;
    @FXML
    public MenuButton filterMenu;
    @FXML
    public CheckBox popularityCheckBox;
    @FXML
    public CheckBox childFriendlinessCheckBox;

    @FXML
    public void initialize() {
        //initial focus is set away from searchfield
        Platform.runLater(() -> headerPane.requestFocus());

        setupCheckBox(popularityCheckBox);
        setupCheckBox(childFriendlinessCheckBox);
    }

    private void setupCheckBox(CheckBox checkBox) {
        checkBox.setOnAction(event -> {
            event.consume();
            filterMenu.show();
        });
    }
}