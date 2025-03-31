    package at.technikum_wien.tourplanner.view;

    import at.technikum_wien.tourplanner.viewmodel.AddLogViewModel;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;

    import java.time.format.DateTimeFormatter;

    public class AddLogController {
        private final AddLogViewModel addLogViewModel;
        //add/edit button
        @FXML private Button addLogButton;
        @FXML private Label titleLabel;
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        private static final String ORIGINAL_STYLE = "-fx-background-color: #DEDBD6;" +
                "-fx-border-radius: 25px;" +
                "-fx-background-radius: 25px;" +
                "-fx-padding: 10px;";
        //input fields
        @FXML private TextField ratingTextField;
        @FXML private TextField dateTextField;
        @FXML private TextField durationTextField;
        @FXML private TextField distanceTextField;
        @FXML private TextArea commentTextArea;
        @FXML private ComboBox<String> difficultyComboBox;
        @FXML private DatePicker hiddenDatePicker;
        public AddLogController(AddLogViewModel addLogViewModel) {
            this.addLogViewModel = addLogViewModel;
        }

        @FXML public void initialize() {
            ratingTextField.textProperty().bindBidirectional(addLogViewModel.ratingProperty());
            dateTextField.textProperty().bindBidirectional(addLogViewModel.dateProperty());
            durationTextField.textProperty().bindBidirectional(addLogViewModel.durationProperty());
            distanceTextField.textProperty().bindBidirectional(addLogViewModel.distanceProperty());
            commentTextArea.textProperty().bindBidirectional(addLogViewModel.commentProperty());
            difficultyComboBox.valueProperty().bindBidirectional(addLogViewModel.difficultyProperty());
            dateTextField.setOnMouseClicked(e -> hiddenDatePicker.show());

            difficultyComboBox.getItems().addAll("Easy", "Moderate", "Hard");

            addLogViewModel.selectedTourLogProperty().addListener((obs, oldLog, newLog) -> {
                if (newLog != null) {
                    addLogButton.setText("Update");
                    titleLabel.setText("EDIT LOG");
                } else {
                    addLogButton.setText("Add Log");
                }
            });
            //when date is selected- update ViewModel
            hiddenDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
                if (newDate != null) {
                    addLogViewModel.dateProperty().set(formatter.format(newDate));
                }
            });

        }
        @FXML private void handleAddOrUpdate() {
            if (addLogViewModel.getSelectedTourLog() != null) {
                addLogViewModel.updateLog();
                addLogViewModel.setSelectedTourLog(null);
                addLogButton.setText("Add Log");
                titleLabel.setText("ADD LOG");
                clearForm();
            }else {
                handleAddLog();
            }
        }
        private void handleAddLog() {
            resetFieldStyles();
            if (!addLogViewModel.addLog()) {
                highlightInvalidFields();
            }else{
                clearForm();
            }
        }

        private void resetFieldStyles() {
            dateTextField.setStyle(ORIGINAL_STYLE);
            durationTextField.setStyle(ORIGINAL_STYLE);
            distanceTextField.setStyle(ORIGINAL_STYLE);
            commentTextArea.setStyle(ORIGINAL_STYLE);
            difficultyComboBox.setStyle(ORIGINAL_STYLE);
            //ratingTextField.setStyle(ORIGINAL_STYLE);
        }

        private void clearForm() {
            //addLogViewModel.ratingProperty().set("");
            addLogViewModel.dateProperty().set("");
            addLogViewModel.durationProperty().set("");
            addLogViewModel.distanceProperty().set("");
            addLogViewModel.commentProperty().set("");
            addLogViewModel.difficultyProperty().set(null); // for ComboBox
        }

        private void highlightInvalidFields() {
            highlightField(dateTextField, addLogViewModel.dateProperty().get());
            highlightField(durationTextField, addLogViewModel.durationProperty().get());
            highlightField(distanceTextField, addLogViewModel.distanceProperty().get());
            highlightField(commentTextArea, addLogViewModel.commentProperty().get());
            highlightField(difficultyComboBox, addLogViewModel.difficultyProperty().get());
            //highlightField(ratingTextField, addLogViewModel.ratingProperty().get());
        }

        private void highlightField(javafx.scene.control.Control field, String value) {
            if (value == null || value.trim().isEmpty()) {
                field.setStyle(ORIGINAL_STYLE + "-fx-border-color: red;");
            } else {
                field.setStyle(ORIGINAL_STYLE);
            }
        }

    }
