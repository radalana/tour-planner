package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.TourDetailsViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.web.WebView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class TourDetailsController {
    private final TourDetailsViewModel tourDetailsViewModel;
    // FXML-injected components
    @FXML private TextField nameDetails;
    @FXML private TextField descriptionDetails;
    @FXML private TextField transportTypeDetails;
    @FXML private ComboBox<String> transportTypeComboBox; //for edit
    @FXML private TextField fromDetails;
    @FXML private TextField toDetails;
    @FXML private TextField distanceDetails;
    @FXML private TextField estimatedTimeDetails;
    @FXML private WebView mapView;
    @FXML private Text mapPlaceholder;

    @FXML private Label nameErrorLabel;
    @FXML private Label descriptionErrorLabel;
    @FXML private Label fromErrorLabel;
    @FXML private Label toErrorLabel;


    @FXML private Button editButton;
    private final Popup suggestionsPopup = new Popup();

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }
    @FXML public void initialize() {
        initAutocomplete();
        //binding
        bind();

        tourDetailsViewModel.nameProperty().addListener((observable, oldValue, newValue) -> {
            tourDetailsViewModel.validate();
                if (newValue.trim().isEmpty()) {
                    //nameErrorLabel.setVisible(true);
                    nameErrorLabel.setText("Name cannot be empty");
                } else {
                    nameErrorLabel.setText("");
                }
        });

        tourDetailsViewModel.descriptionProperty().addListener((observable, oldValue, newValue) -> {
            tourDetailsViewModel.validate();
            if (newValue.trim().isEmpty()) {
                //nameErrorLabel.setVisible(true);
                descriptionErrorLabel.setText("Description cannot be empty");
            } else {
                descriptionErrorLabel.setText("");
            }
        });

        tourDetailsViewModel.fromProperty().addListener((observable, oldValue, newValue) -> {
            tourDetailsViewModel.validate();
            if (newValue.trim().isEmpty()) {
                fromErrorLabel.setText("start point cannot be empty");
            }else {
                fromErrorLabel.setText("");
            }
        });

        tourDetailsViewModel.toProperty().addListener((observable, oldValue, newValue) -> {
            tourDetailsViewModel.validate();
            if (newValue.trim().isEmpty()) {
                toErrorLabel.setText("End point cannot be empty");
            }else {
                toErrorLabel.setText("");
            }
        });



        String from = tourDetailsViewModel.fromProperty().get();
        String to = tourDetailsViewModel.toProperty().get();

        String transport = URLEncoder.encode(tourDetailsViewModel.transportTypeProperty().get().toLowerCase().replace(" ", "-"), StandardCharsets.UTF_8);

        String mapUrl = "http://localhost:8080/map.html?from=" + from + "&to=" + to + "&transport=" + transport;
        mapView.getEngine().load(mapUrl);

        //hide the placeholder text once the map loads
        mapView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                mapView.setVisible(true);
                mapPlaceholder.setVisible(false);
            }
        });


    }


    public void deleteTour(ActionEvent actionEvent) {
        tourDetailsViewModel.deleteTour();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void openLogs(ActionEvent actionEvent) {
        tourDetailsViewModel.openLogs();
    }


    public void activateEditTour(ActionEvent actionEvent) {
        if (editButton.getText().equals("EDIT TOUR")) {
            nameDetails.setEditable(true);
            descriptionDetails.setEditable(true);
            transportTypeDetails.setEditable(true);
            fromDetails.setEditable(true);
            toDetails.setEditable(true);


            transportTypeDetails.setVisible(false);
            transportTypeDetails.setManaged(false);

            transportTypeComboBox.setVisible(true);
            transportTypeComboBox.setManaged(true);

            editButton.setText("UPDATE");
            if (editButton.getText().equals("UPDATE")) {
                editButton.disableProperty().bind(tourDetailsViewModel.validFormProperty().not());
            }
            tourDetailsViewModel.validate();
        } else {
            tourDetailsViewModel.updateTour()
                    .exceptionally(ex -> {
                        Platform.runLater(() -> showAlert(Alert.AlertType.ERROR,
                                "Update Failed",
                                extractMessage(ex)));
                        return null;
                    });

            nameDetails.setEditable(false);
            descriptionDetails.setEditable(false);
            transportTypeDetails.setEditable(false);
            fromDetails.setEditable(false);
            toDetails.setEditable(false);
            distanceDetails.setEditable(false);
            estimatedTimeDetails.setEditable(false);
            transportTypeDetails.setVisible(true);
            transportTypeDetails.setManaged(true);
            transportTypeComboBox.setVisible(false);
            transportTypeComboBox.setManaged(false);


            editButton.setText("SAVED");
        }

    }

    @FXML
    private void generateTourReport() {
        Long tourId = tourDetailsViewModel.getTourId();
        if (tourId == null) {
            System.err.println("Cannot generate report.");
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/reports/tour/" + tourId))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Tour Report");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                fileChooser.setInitialFileName("tour-report-" + tourId + ".pdf");

                File file = fileChooser.showSaveDialog(mapView.getScene().getWindow());

                if (file != null) {
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(response.body());
                    }

                    //open in default viewer
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                    }
                }

            } else {
                System.err.println("Failed to download report. HTTP Status: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initAutocomplete() {
        fromDetails.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            String input = fromDetails.getText();
            if (input.length() >= 2) {
                fetchAndShowPopupSuggestions(input, fromDetails);
            }
        });
        toDetails.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            String input = toDetails.getText();
            if (input.length() >= 2) fetchAndShowPopupSuggestions(input, toDetails);
        });
    }

    private void fetchAndShowPopupSuggestions(String input, TextField textField) {
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() {
                return tourDetailsViewModel.fetchLocationSuggestions(input);
            }
        };

        task.setOnSucceeded(e -> {
            List<String> suggestions = task.getValue();
            if (!suggestions.isEmpty()) {
                showSuggestionsPopup(suggestions, textField);
            }
        });

        new Thread(task).start();
    }
    private void showSuggestionsPopup(List<String> suggestions, TextField inputField) {
        ListView<String> suggestionList = new ListView<>();

        suggestionList.getItems().addAll(suggestions);
        suggestionList.setPrefHeight(Math.min(150, suggestions.size() * 24));

        suggestionList.getStyleClass().add("suggestion-list");
        suggestionList.setOnMouseClicked(event -> {
            inputField.setText(suggestionList.getSelectionModel().getSelectedItem());
            suggestionsPopup.hide();
        });

        suggestionsPopup.getContent().clear();
        suggestionsPopup.getContent().add(suggestionList);
        suggestionsPopup.setAutoHide(true);

        Bounds bounds = inputField.localToScreen(inputField.getBoundsInLocal());
        suggestionsPopup.show(inputField, bounds.getMinX(), bounds.getMaxY());
    }
    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private String extractMessage(Throwable ex) {
        Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
        return (cause.getMessage() != null) ? cause.getMessage() : "Unknown error occurred";
    }

    private void bind() {
        editButton.disableProperty().bind(tourDetailsViewModel.validFormProperty().not());
        nameDetails.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        descriptionDetails.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
        transportTypeDetails.textProperty().bindBidirectional(tourDetailsViewModel.transportTypeProperty());
        fromDetails.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        toDetails.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        Bindings.bindBidirectional(distanceDetails.textProperty(), tourDetailsViewModel.distanceProperty(), new NumberStringConverter());
        estimatedTimeDetails.textProperty().bindBidirectional(tourDetailsViewModel.estimatedTimeProperty());
    }
}
