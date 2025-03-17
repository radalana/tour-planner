package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.NewTourCloseListener;
import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

public class NewTourController {
    @Setter private NewTourCloseListener listener;

    private final NewTourViewModel newTourViewModel;
    @Getter @FXML private AnchorPane newTourContainer;

    public NewTourController(NewTourViewModel newTourViewModel) {
        this.newTourViewModel = newTourViewModel;
    }

    @FXML public void initialize() {
        newTourContainer.setVisible(false);
        newTourContainer.setPickOnBounds(false);
    }

    @FXML
    private void closeNewTour() {
        if (listener != null) listener.onNewTourClosed();
        newTourContainer.setVisible(false);
    }
}
