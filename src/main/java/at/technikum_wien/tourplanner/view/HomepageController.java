package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.AppEventAggregator;
import at.technikum_wien.tourplanner.FXMLDependencyInjection;
import at.technikum_wien.tourplanner.NewTourCloseListener;
import at.technikum_wien.tourplanner.events.TourSelectedEvent;
import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class HomepageController {

    //Controllers
    @FXML private TourTableController tourTableController;
    @FXML private NewTourController newTourController;//injected controller of newTour.fxml

    //Elements
    @FXML private AnchorPane rootLayout;
    @FXML private AnchorPane overlayPane;

    private final HomepageViewModel homepageViewModel;
    public HomepageController(HomepageViewModel homepageViewModel) {
        this.homepageViewModel = homepageViewModel;

    }

    @FXML public void initialize() {
        newTourController.setListener(new NewTourCloseListener() {
            @Override
            public void onNewTourClosed() {
                overlayPane.setVisible(false);
                overlayPane.setMouseTransparent(true);
            }
        });
        /*
        homepageViewModel.getAppEventAggregator().subscribe(TourSelectedEvent.class, event -> {
            loadTourDetailsView();
        })
         */
    }

    @FXML private void showPopupNewTour(ActionEvent actionEvent) {
        newTourController.getNewTourContainer().setVisible(true);
        overlayPane.setVisible(true);
        //TODO not clickable wenn popup
        //TODO darker
    }

    private void loadTourDetailsView() {
        try {
            Parent tourDetailsView = FXMLDependencyInjection.load("tourDetails.fxml");
            Platform.runLater(() -> {
                rootLayout.getChildren().setAll(tourDetailsView);
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}