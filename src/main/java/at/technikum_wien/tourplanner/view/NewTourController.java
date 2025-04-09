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
    @Getter @FXML private StackPane newTourContainer;
    //input fields
    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField fromTextField;
    @FXML private TextField toTextField;
    @FXML private ComboBox<String> transportTypeComboBox;
    @FXML private TextField distanceTextField;
    @FXML private TextField estTimeTextField;


    //original field styling
    private static final String ORIGINAL_STYLE = "-fx-background-color: #DEDBD6;" +
            "-fx-border-radius: 25px;" +
            "-fx-background-radius: 25px;" +
            "-fx-padding: 12px;" +
            "-fx-font-size: 16px;";

    public NewTourController(NewTourViewModel newTourViewModel) {
        this.newTourViewModel = newTourViewModel;
    }

    @FXML public void initialize() {
        newTourContainer.visibleProperty().bindBidirectional(newTourViewModel.isNewTourContainerVisibleProperty());
        bindTableColumnsToViewModel();

        //automatically reset form every time it's shown
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
        newTourViewModel.estTimeProperty().set("");

        //reset all styles
        resetFieldStyles();
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
        if (newTourViewModel.createTour()) {
            resetFieldStyles();
            closeNewTour();
        }

    }

    public void openNewTourForm() {
        // Reset ViewModel values
        newTourViewModel.nameProperty().set("");
        newTourViewModel.descriptionProperty().set("");
        newTourViewModel.fromProperty().set("");
        newTourViewModel.toProperty().set("");
        newTourViewModel.transportTypeProperty().set(null);
        newTourViewModel.distanceProperty().set(0.0);
        newTourViewModel.estTimeProperty().set("");

        // Reset visual styles
        resetFieldStyles();

        // Show the form
        newTourContainer.setVisible(true);
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
            transportTypeComboBox.setStyle(ORIGINAL_STYLE);
        }

        try {
            double d = Double.parseDouble(distanceTextField.getText());
            if (d <= 0) {
                distanceTextField.setStyle(ORIGINAL_STYLE + " -fx-border-color: red;");
            } else {
                distanceTextField.setStyle(ORIGINAL_STYLE);
            }
        } catch (NumberFormatException e) {
            distanceTextField.setStyle(ORIGINAL_STYLE + " -fx-border-color: red;");
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
        estTimeTextField.setStyle(ORIGINAL_STYLE);
        distanceTextField.setStyle(ORIGINAL_STYLE);
        transportTypeComboBox.setStyle(ORIGINAL_STYLE);;
    }
}