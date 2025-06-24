package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;

import java.util.List;

public class NewTourController {
    private final NewTourViewModel newTourViewModel;

    @Getter
    @FXML private StackPane newTourContainer;

    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField fromTextField;
    @FXML private TextField toTextField;
    @FXML private ComboBox<String> transportTypeComboBox;

    // Custom autocomplete views
    @FXML private ListView<String> fromSuggestions;
    @FXML private ListView<String> toSuggestions;

    private final ObservableList<String> fromSuggestionList = FXCollections.observableArrayList();
    private final ObservableList<String> toSuggestionList = FXCollections.observableArrayList();

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
        bindFields();
        initAutocomplete();

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

        fromSuggestions.setVisible(false);
        toSuggestions.setVisible(false);

        resetFieldStyles();
    }

    private void bindFields() {
        nameTextField.textProperty().bindBidirectional(newTourViewModel.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(newTourViewModel.descriptionProperty());
        fromTextField.textProperty().bindBidirectional(newTourViewModel.fromProperty());
        toTextField.textProperty().bindBidirectional(newTourViewModel.toProperty());
        transportTypeComboBox.valueProperty().bindBidirectional(newTourViewModel.transportTypeProperty());
    }

    private void initAutocomplete() {
        fromSuggestions.setItems(fromSuggestionList);
        toSuggestions.setItems(toSuggestionList);

        fromTextField.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            String input = fromTextField.getText();
            if (input.length() >= 2) fetchAutocomplete(input, fromSuggestionList, fromSuggestions);
        });

        toTextField.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            String input = toTextField.getText();
            if (input.length() >= 2) fetchAutocomplete(input, toSuggestionList, toSuggestions);
        });

        fromSuggestions.setOnMouseClicked(e -> {
            String selected = fromSuggestions.getSelectionModel().getSelectedItem();
            if (selected != null) {
                fromTextField.setText(selected);
                fromSuggestions.setVisible(false);
            }
        });

        toSuggestions.setOnMouseClicked(e -> {
            String selected = toSuggestions.getSelectionModel().getSelectedItem();
            if (selected != null) {
                toTextField.setText(selected);
                toSuggestions.setVisible(false);
            }
        });

        fromSuggestions.setVisible(false);
        toSuggestions.setVisible(false);
    }

    private void fetchAutocomplete(String input, ObservableList<String> suggestionList, ListView<String> listView) {
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() {
                return newTourViewModel.fetchLocationSuggestions(input);
            }
        };

        task.setOnSucceeded(e -> {
            suggestionList.setAll(task.getValue());
            listView.setVisible(!suggestionList.isEmpty());
        });

        task.setOnFailed(e -> listView.setVisible(false));

        new Thread(task).start();
    }

    @FXML
    private void closeNewTour() {
        newTourContainer.setVisible(false);
    }

    @FXML
    private void saveNewTour() {
        if (!newTourViewModel.validate()) {
            highlightInvalidFields();
            return;
        }

        if (newTourViewModel.createTour()) {
            resetFieldStyles();
            closeNewTour();
        }
    }

    public void handleImport(ActionEvent event) {
        // no-op
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

    private void highlightField(Control field, String value) {
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
