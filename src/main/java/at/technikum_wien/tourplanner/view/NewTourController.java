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
import lombok.Setter;

public class NewTourController {
    private final NewTourViewModel newTourViewModel;
    @Getter @FXML private StackPane newTourContainer;
    //input fields
    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField fromTextField;
    @FXML private TextField toTextField;
    @FXML private ComboBox<String> transportTypeComboBox;
    @FXML private TextField distanceTextField;
    @FXML private TextField estTimeTextField;

    public NewTourController(NewTourViewModel newTourViewModel) {
        this.newTourViewModel = newTourViewModel;
    }

    @FXML public void initialize() {
        newTourContainer.visibleProperty().bindBidirectional(newTourViewModel.isNewTourContainerVisibleProperty());
        bindTableColumnsToViewModel();

    }

    @FXML
    private void closeNewTour() {
        newTourContainer.setVisible(false);
    }

    @FXML private void saveNewTour() {

        if (!newTourViewModel.validate()) {
            System.out.println("not valid");
            highlightInvalidFields();
            return;
        }
        if (newTourViewModel.saveTour()) {
            resetFieldStyles();
            closeNewTour();
        }

    }
    public void handleImport(ActionEvent actionEvent) {
    }

    //binding on live data check/validation
    private void bindTableColumnsToViewModel() {
        nameTextField.textProperty().bindBidirectional(newTourViewModel.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(newTourViewModel.descriptionProperty());
        fromTextField.textProperty().bindBidirectional(newTourViewModel.fromProperty());
        toTextField.textProperty().bindBidirectional(newTourViewModel.toProperty());
        transportTypeComboBox.valueProperty().bindBidirectional(newTourViewModel.transportTypeProperty());
        Bindings.bindBidirectional(distanceTextField.textProperty(), newTourViewModel.distanceProperty(), new NumberStringConverter());
        estTimeTextField.textProperty().bindBidirectional(newTourViewModel.estTimeProperty());
    }

    private void highlightInvalidFields() {
        highlightField(nameTextField, newTourViewModel.nameProperty().get());
        highlightField(descriptionTextArea, newTourViewModel.descriptionProperty().get());
        highlightField(fromTextField, newTourViewModel.fromProperty().get());
        highlightField(toTextField, newTourViewModel.toProperty().get());
        highlightField(estTimeTextField, newTourViewModel.estTimeProperty().get());

        if (transportTypeComboBox.getValue() == null || transportTypeComboBox.getValue().isEmpty()) {
            transportTypeComboBox.setStyle("-fx-border-color: red;");
        } else {
            transportTypeComboBox.setStyle("");
        }

        try {
            double d = Double.parseDouble(distanceTextField.getText());
            if (d <= 0) {
                distanceTextField.setStyle("-fx-border-color: red;");
            } else {
                distanceTextField.setStyle("");
            }
        } catch (NumberFormatException e) {
            distanceTextField.setStyle("-fx-border-color: red;");
        }
    }

    private void highlightField(javafx.scene.control.Control field, String value) {
        if (value == null || value.trim().isEmpty()) {
            field.setStyle("-fx-border-color: red;");
        } else {
            field.setStyle("");
        }
    }

    private void resetFieldStyles() {
        nameTextField.setStyle("");
        descriptionTextArea.setStyle("");
        fromTextField.setStyle("");
        toTextField.setStyle("");
        estTimeTextField.setStyle("");
        distanceTextField.setStyle("");
        transportTypeComboBox.setStyle("");
    }
}