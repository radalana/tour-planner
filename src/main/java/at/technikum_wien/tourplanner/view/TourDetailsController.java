package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.TourDetailsViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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



public class TourDetailsController {
    private final TourDetailsViewModel tourDetailsViewModel;
    // FXML-injected components
    @FXML private TextField nameDetails;
    @FXML private TextField descriptionDetails;
    @FXML private TextField transportTypeDetails;
    @FXML private TextField fromDetails;
    @FXML private TextField toDetails;
    @FXML private TextField distanceDetails;
    @FXML private TextField estimatedTimeDetails;
    @FXML private WebView mapView;
    @FXML private Text mapPlaceholder;


    @FXML private Button editButton;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }
    @FXML public void initialize() {
        tourDetailsViewModel.loadTourData();
        //one-way binding
        nameDetails.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        descriptionDetails.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
        transportTypeDetails.textProperty().bindBidirectional(tourDetailsViewModel.transportTypeProperty());
        fromDetails.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        toDetails.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        Bindings.bindBidirectional(distanceDetails.textProperty(), tourDetailsViewModel.distanceProperty(), new NumberStringConverter());
        estimatedTimeDetails.textProperty().bindBidirectional(tourDetailsViewModel.estimatedTimeProperty());

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
            distanceDetails.setEditable(true);
            estimatedTimeDetails.setEditable(true);

            editButton.setText("UPDATE");
        } else {
            tourDetailsViewModel.updateTour();

            nameDetails.setEditable(false);
            descriptionDetails.setEditable(false);
            transportTypeDetails.setEditable(false);
            fromDetails.setEditable(false);
            toDetails.setEditable(false);
            distanceDetails.setEditable(false);
            estimatedTimeDetails.setEditable(false);

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

}
