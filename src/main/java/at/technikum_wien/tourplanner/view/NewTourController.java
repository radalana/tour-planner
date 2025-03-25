package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.NewTourCloseListener;
import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;
import lombok.Setter;

public class NewTourController {
    private final NewTourViewModel newTourViewModel;
    @Setter private NewTourCloseListener listener;

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
        newTourContainer.setVisible(false);
        newTourContainer.setPickOnBounds(false);

        bindFieldsToViewModel();

    }

    @FXML
    private void closeNewTour() {
        newTourContainer.setVisible(false);
    }

   @FXML private void saveNewTour() {

       if (!newTourViewModel.saveTour()) {
           System.out.println("bla bla sm notification in view");
       }

       if (listener != null) {
           listener.onNewTourClosed();
           //TODO refactor close functionality
           //closeNewTour();
       }

   }

   private void bindFieldsToViewModel() {
       //binding on live data check/validation
       nameTextField.textProperty().bindBidirectional(newTourViewModel.nameProperty());
       descriptionTextArea.textProperty().bindBidirectional(newTourViewModel.descriptionProperty());
       fromTextField.textProperty().bindBidirectional(newTourViewModel.fromProperty());
       toTextField.textProperty().bindBidirectional(newTourViewModel.toProperty());
       transportTypeComboBox.valueProperty().bindBidirectional(newTourViewModel.transportTypeProperty());
       Bindings.bindBidirectional(
               distanceTextField.textProperty(),
               newTourViewModel.distanceProperty(),
               new NumberStringConverter()
       );
       estTimeTextField.textProperty().bindBidirectional(newTourViewModel.estTimeProperty());
   }
}
