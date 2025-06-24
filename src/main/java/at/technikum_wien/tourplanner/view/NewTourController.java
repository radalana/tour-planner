package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;

public class NewTourController {
    private final NewTourViewModel newTourViewModel;

    @Getter
    @FXML
    private StackPane newTourContainer;

    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField fromTextField;
    @FXML private TextField toTextField;
    @FXML private ComboBox<String> transportTypeComboBox;

    private static final String ORIGINAL_STYLE = "-fx-background-color: #DEDBD6;" +
            "-fx-border-radius: 25px;" +
            "-fx-background-radius: 25px;" +
            "-fx-padding: 12px;" +
            "-fx-font-size: 16px;";

    public NewTourController(NewTourViewModel newTourViewModel) {
        this.newTourViewModel = newTourViewModel;
    }

    @FXML
    public void initialize() {
        newTourContainer.visibleProperty().bindBidirectional(newTourViewModel.isNewTourContainerVisibleProperty());
        bindTableColumnsToViewModel();

        newTourContainer.visibleProperty().addListener((obs, wasVisible, isNowVisible) -> {
            if (isNowVisible) {
                resetForm();
            }
        });
    }

    private void resetForm() {
        newTourViewModel.nameProperty().set("");
        newTourViewModel.descriptionProperty().set("");
        newTourViewModel.fromProperty().set("");
        newTourViewModel.toProperty().set("");
        newTourViewModel.transportTypeProperty().set(null);
        newTourViewModel.distanceProperty().set(0.0);
        newTourViewModel.estTimeProperty().set(0.0);
        resetFieldStyles();
    }

    @FXML
    private void closeNewTour() {
        newTourContainer.setVisible(false);
    }

    @FXML
    private void saveNewTour() {
        if (!newTourViewModel.validate()) {
            System.out.println("not valid");
            highlightInvalidFields();
            return;
        }
        if (newTourViewModel.createTour()) {
            resetFieldStyles();
            closeNewTour();
        }
    }

    public void openNewTourForm() {
        newTourViewModel.nameProperty().set("");
        newTourViewModel.descriptionProperty().set("");
        newTourViewModel.fromProperty().set("");
        newTourViewModel.toProperty().set("");
        newTourViewModel.transportTypeProperty().set(null);
        newTourViewModel.distanceProperty().set(0.0);
        newTourViewModel.estTimeProperty().set(0.0);
        resetFieldStyles();
        newTourContainer.setVisible(true);
    }

    public void handleImport(ActionEvent actionEvent) {
        // Placeholder for import logic
    }

    private void bindTableColumnsToViewModel() {
        nameTextField.textProperty().bindBidirectional(newTourViewModel.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(newTourViewModel.descriptionProperty());
        fromTextField.textProperty().bindBidirectional(newTourViewModel.fromProperty());
        toTextField.textProperty().bindBidirectional(newTourViewModel.toProperty());
        transportTypeComboBox.valueProperty().bindBidirectional(newTourViewModel.transportTypeProperty());
    }

    private void highlightInvalidFields() {
        highlightField(nameTextField, newTourViewModel.nameProperty().get());
        highlightField(descriptionTextArea, newTourViewModel.descriptionProperty().get());
        highlightField(fromTextField, newTourViewModel.fromProperty().get());
        highlightField(toTextField, newTourViewModel.toProperty().get());

        if (transportTypeComboBox.getValue() == null || transportTypeComboBox.getValue().isEmpty()) {
            transportTypeComboBox.setStyle("-fx-border-color: red;");
        } else {
            transportTypeComboBox.setStyle(ORIGINAL_STYLE);
        }
    }

    private void highlightField(javafx.scene.control.Control field, String value) {
        if (value == null || value.trim().isEmpty()) {
            field.setStyle(ORIGINAL_STYLE + " -fx-border-color: red;");
        } else {
            field.setStyle(ORIGINAL_STYLE);
        }
    }

    private void resetFieldStyles() {
        nameTextField.setStyle(ORIGINAL_STYLE);
        descriptionTextArea.setStyle(ORIGINAL_STYLE);
        fromTextField.setStyle(ORIGINAL_STYLE);
        toTextField.setStyle(ORIGINAL_STYLE);
        transportTypeComboBox.setStyle(ORIGINAL_STYLE);
    }
}
