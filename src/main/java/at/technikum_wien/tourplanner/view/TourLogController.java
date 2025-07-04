package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.model.TourLog;
import at.technikum_wien.tourplanner.viewmodel.TourLogViewModel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

public class TourLogController {
    private TourLogViewModel viewModel;
    //fxml components
    @FXML private TableView<TourLog> logsTableView;
    @FXML private TableColumn<TourLog, String> ratingColumn;
    @FXML private TableColumn<TourLog, String> dateColumn;
    @FXML private TableColumn<TourLog, String> distanceColumn;
    @FXML private TableColumn<TourLog, String> durationColumn;
    @FXML private TableColumn<TourLog, String> commentColumn;
    @FXML private TableColumn<TourLog, String> difficultyColumn;
    @FXML private TextField searchLogTextField;
    @FXML private AnchorPane logsRoot;

    public TourLogController(TourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML private void initialize() {
        viewModel.syncLogs();
        if (viewModel.getSelectedTour().get() != null) {
            logsTableView.setItems(viewModel.getSelectedTour().get().getObservableLogs());
        }
        //binding
        bindTableColumnsToProperties();

        //right-click
        logsTableView.setRowFactory(tv -> {
            TableRow<TourLog> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Delete");
            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction((ActionEvent event) -> {
                TourLog tourLog = row.getItem();
                System.out.println("edit");
                viewModel.editLog(tourLog);
            });

            delete.setOnAction((ActionEvent event) -> {
                TourLog tourLog = row.getItem();
                System.out.println("delete button pressed");
                viewModel.deleteLog(tourLog);


            });
            contextMenu.getItems().addAll(edit, delete);
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }else{
                    contextMenu.hide();
                }
            });
            return row;
        });
        Platform.runLater(() -> logsRoot.requestFocus());
    }

    @FXML
    private void handleSearchLogs() {
        System.out.println("search logs pressed");
        String query = searchLogTextField.getText();
        System.out.println(query);
        viewModel.searchLogs(query);
    }

    private void bindTableColumnsToProperties() {
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeProperty());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().totalDistanceProperty());
        durationColumn.setCellValueFactory(cellData -> {
            int days = cellData.getValue().durationDaysProperty().get();
            int hours = cellData.getValue().durationHoursProperty().get();
            int minutes = cellData.getValue().durationMinutesProperty().get();
            return  new SimpleStringProperty(String.format("%02d days %02d:%02d", days, hours, minutes));
        });
        commentColumn.setCellValueFactory(commentData -> commentData.getValue().commentProperty());
        difficultyColumn.setCellValueFactory(cellData -> {
            double diff = cellData.getValue().getDifficulty();
            String label = switch ((int) diff) {
                case 1 -> "Easy";
                case 2 -> "Moderate";
                case 3 -> "Hard";
                default -> "Unknown";
            };
            return new javafx.beans.property.SimpleStringProperty(label);
        });
    }


}
