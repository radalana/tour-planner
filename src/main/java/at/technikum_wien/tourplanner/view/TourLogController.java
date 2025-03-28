package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.model.TourLog;
import at.technikum_wien.tourplanner.viewmodel.TourLogViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public TourLogController(TourLogViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @FXML private void initialize() {
        //get lists of logs
        logsTableView.setItems(viewModel.getLogs());
        logsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //one-way binding to columns
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("totalDistance"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        //right-click
        logsTableView.setRowFactory(tv -> {
            TableRow<TourLog> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Delete");
            MenuItem edit = new MenuItem("Edit");
            edit.setOnAction((ActionEvent event) -> {
                TourLog tourLog = row.getItem();
                System.out.println("edit");
            });

            delete.setOnAction((ActionEvent event) -> {
                TourLog tourLog = row.getItem();
                System.out.println("delete");

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



}
