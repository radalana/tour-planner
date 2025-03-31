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

    @FXML private Button editButton;

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }
    @FXML public void initialize() {
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

        //updateButton.setVisible(false);
    }

    public void activateEditTour(ActionEvent actionEvent) {
        if (editButton.getText().equals("EDIT TOUR")) {
            nameDetails.setEditable(true);
            descriptionDetails.setEditable(true);
            fromDetails.setEditable(true);
            toDetails.setEditable(true);
            distanceDetails.setEditable(true);
            estimatedTimeDetails.setEditable(true);

            editButton.setText("UPDATE");
        } else {
            tourDetailsViewModel.updateTour();

            nameDetails.setEditable(false);
            descriptionDetails.setEditable(false);
            fromDetails.setEditable(false);
            toDetails.setEditable(false);
            distanceDetails.setEditable(false);
            estimatedTimeDetails.setEditable(false);

            editButton.setText("SAVED");
        }

    }
}
