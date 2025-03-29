package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.AddLogViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddLogController {
    private final AddLogViewModel addLogViewModel;
    //add/edit button
    @FXML private Button addLogButton;

    //input fields
    @FXML private TextField ratingTextField;
    @FXML private TextField dateTextField;
    @FXML private TextField durationTextField;
    @FXML private TextField distanceTextField;
    @FXML private TextArea commentTextArea;
    @FXML private TextField difficultyTextField;
    public AddLogController(AddLogViewModel addLogViewModel) {
        this.addLogViewModel = addLogViewModel;
    }

    @FXML public void initialize() {
        ratingTextField.textProperty().bindBidirectional(addLogViewModel.ratingProperty());
        dateTextField.textProperty().bindBidirectional(addLogViewModel.dateProperty());
        durationTextField.textProperty().bindBidirectional(addLogViewModel.durationProperty());
        distanceTextField.textProperty().bindBidirectional(addLogViewModel.distanceProperty());
        commentTextArea.textProperty().bindBidirectional(addLogViewModel.commentProperty());
        difficultyTextField.textProperty().bindBidirectional(addLogViewModel.difficultyProperty());

        addLogViewModel.selectedTourLogProperty().addListener((obs, oldLog, newLog) -> {
            if (newLog != null) {
                addLogButton.setText("Update");
            } else {
                addLogButton.setText("Add Log");
            }
        });

    }
    @FXML private void handleAddOrUpdate() {
        if (addLogViewModel.getSelectedTourLog() != null) {
            addLogViewModel.updateLog();
            addLogViewModel.setSelectedTourLog(null);
            addLogButton.setText("Add Log");
        }else {
            addLogViewModel.addLog();
        }
    }
    @FXML private void handleAddLog() {
        if (addLogViewModel.addLog()) {
            resetFieldStyles();
        }
        highlightInvalidFields();
    }

    //TODO Gabriela
    private void resetFieldStyles() {}
    private void highlightInvalidFields() {}

}
