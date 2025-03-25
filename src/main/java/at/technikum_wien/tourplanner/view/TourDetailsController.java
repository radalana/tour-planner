package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.TourDetailsViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }
    @FXML public void initialize() {
        tourDetailsViewModel.loadTourData();

        //one-way binding
        nameDetails.textProperty().bind(tourDetailsViewModel.nameProperty());
        descriptionDetails.textProperty().bind(tourDetailsViewModel.descriptionProperty());
        fromDetails.textProperty().bind(tourDetailsViewModel.fromProperty());
        toDetails.textProperty().bind(tourDetailsViewModel.toProperty());
        distanceDetails.textProperty().bind(
                Bindings.convert(tourDetailsViewModel.distanceProperty())
        );
        estimatedTimeDetails.textProperty().bind(tourDetailsViewModel.estimatedTimeProperty());
    }
}
