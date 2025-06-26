package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.FXMLDependencyInjection;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.TableCell;

public class TourTableController {
    // FXML UI components
    @FXML private TableView<Tour> tourTableView;
    @FXML private TableColumn<Tour,String> tourNameColumn;
    @FXML private TableColumn<Tour,String> transportTypeColumn;
    @FXML private TableColumn<Tour,String> fromColumn;
    @FXML private TableColumn<Tour,String> toColumn;
    @FXML private TableColumn<Tour,Double> distanceColumn;
    @FXML private TableColumn<Tour,String> estimatedTimeColumn;

    private final TourTableViewModel tourTableViewModel;

    public TourTableController(TourTableViewModel tourTableViewModel) {
        this.tourTableViewModel = tourTableViewModel;
    }
    @FXML
    public void initialize() {
        System.out.println("Initializing");
        //get list of tours, subscription
        tourTableViewModel.syncTours();
        tourTableView.setItems(tourTableViewModel.getTours());

        //adjust column widths
        tourTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bindTableColumnsToProperties();

        tourTableView.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Tour tour = row.getItem();
                    tourTableViewModel.selectTour(tour);
                    openDetailView();
                }
            });
            return row;
        });

        //debug
        tourTableView.getItems().addListener((ListChangeListener<Tour>) change -> {
            System.out.println("Table item count changed: " + tourTableView.getItems().size());
        });
    }

    //TODO for clean mvvm change to Mediator
    private void openDetailView() {
        try {
            Parent root = FXMLDependencyInjection.load("tourDetails.fxml");
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("User Details");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindTableColumnsToProperties() {
        //how/what to show in each column
        tourNameColumn.setCellValueFactory(cellData -> cellData.getValue().tourNameProperty());
        transportTypeColumn.setCellValueFactory(cellData -> cellData.getValue().transportTypeProperty());
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        toColumn.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        estimatedTimeColumn.setCellValueFactory(cellData -> cellData.getValue().estimatedTimeProperty());
        // Distance column: binds to DoubleProperty but displays 2 decimals
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().distanceProperty().asObject());
        distanceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

    }

}
