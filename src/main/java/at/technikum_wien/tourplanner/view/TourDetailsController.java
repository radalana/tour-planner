package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.TourDetailsViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class TourDetailsController {
    private final TourDetailsViewModel tourDetailsViewModel;
    // FXML-injected components
    @FXML private TextField nameDetails;
    @FXML private TextField descriptionDetails;
    @FXML private TextField fromDetails;
    @FXML private TextField toDetails;
    @FXML private TextField distanceDetails;
    @FXML private TextField estimatedTimeDetails;

    @FXML private Button updateButton;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }
    @FXML public void initialize() {
        setupEditableField(nameDetails);
        setupEditableField(descriptionDetails);
        setupEditableField(fromDetails);
        setupEditableField(toDetails);
        setupEditableField(distanceDetails);
        setupEditableField(estimatedTimeDetails);

        tourDetailsViewModel.loadTourData();

        //one-way binding
        nameDetails.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        descriptionDetails.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
        fromDetails.textProperty().bindBidirectional(tourDetailsViewModel.fromProperty());
        toDetails.textProperty().bindBidirectional(tourDetailsViewModel.toProperty());
        Bindings.bindBidirectional(distanceDetails.textProperty(), tourDetailsViewModel.distanceProperty(), new NumberStringConverter());
        estimatedTimeDetails.textProperty().bindBidirectional(tourDetailsViewModel.estimatedTimeProperty());


    }

    public void deleteTour(ActionEvent actionEvent) {
        tourDetailsViewModel.deleteTour();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void setupEditableField(TextField field) {
        field.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println("Double click detected on: " + field.getId()); // Debug
                field.setEditable(true);
                field.requestFocus();
                field.selectAll();
                updateButton.setVisible(true);
            }
        });

        field.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    field.setEditable(false);
                    updateButton.setVisible(true); // show update Button
                    break;
                case ESCAPE:
                    field.setEditable(false);
                    updateButton.setVisible(false); // cancel edititing
                    break;
            }
        });
    }

    public void openLogs(ActionEvent actionEvent) {
        tourDetailsViewModel.openLogs();
    }

    @FXML private void handleUpdateTourDetails() {
        //logic for update
        tourDetailsViewModel.updateTour();

        //set back to not editable
        nameDetails.setEditable(false);
        descriptionDetails.setEditable(false);
        fromDetails.setEditable(false);
        toDetails.setEditable(false);
        distanceDetails.setEditable(false);
        estimatedTimeDetails.setEditable(false);

        updateButton.setVisible(false);
    }
}
