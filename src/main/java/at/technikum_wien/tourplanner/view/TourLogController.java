package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.model.TourLog;
import at.technikum_wien.tourplanner.viewmodel.TourLogViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

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


    public TourLogController(TourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @FXML private void initialize() {
        System.out.println("✅ Controller initialized");
        viewModel.syncLogs();
        if (viewModel.getSelectedTour().get() != null) {
            logsTableView.setItems(viewModel.getSelectedTour().get().getObservableLogs());
        }

        logsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().totalTimeProperty());
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
