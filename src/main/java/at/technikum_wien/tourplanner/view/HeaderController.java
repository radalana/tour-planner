package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HeaderViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("save summary report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("summary-report.pdf");

        File file = fileChooser.showSaveDialog(headerPane.getScene().getWindow());

        if (file != null) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/reports/summary"))
                        .GET()
                        .build();

                HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

                if (response.statusCode() == 200) {
                    Files.copy(response.body(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Summary report saved: " + file.getAbsolutePath());
                    // automatically open the PDF after saving
                    if (java.awt.Desktop.isDesktopSupported()) {
                        java.awt.Desktop.getDesktop().open(file);
                    }
                } else {
                    System.err.println("Failed to download report. Status: " + response.statusCode());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSearch() {
        String query = searchTextField.getText(); //only search text
        headerViewModel.searchTours(query);
    }

}