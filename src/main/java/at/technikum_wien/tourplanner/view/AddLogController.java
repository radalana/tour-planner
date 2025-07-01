    package at.technikum_wien.tourplanner.view;

    import at.technikum_wien.tourplanner.viewmodel.AddLogViewModel;
    import javafx.application.Platform;
    import javafx.beans.property.IntegerProperty;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;

    import java.time.format.DateTimeFormatter;
    import java.util.Objects;

    public class AddLogController {
        private final static String ADD_LOG_TITLE = "Share your experience";
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

        @FXML private Spinner<Integer> daysSpinner;
        @FXML private Spinner<Integer> hoursSpinner;
        @FXML private Spinner<Integer> minutesSpinner;

        @FXML private TextField distanceTextField;
        @FXML private TextArea commentTextArea;
        @FXML private ComboBox<String> difficultyComboBox;
        @FXML private DatePicker hiddenDatePicker;

        @FXML private ImageView pusheen1, pusheen2, pusheen3, pusheen4, pusheen5;
        private ImageView[] pusheenViews;

        private Image activeImg;
        private Image inactiveImg;

        public AddLogController(AddLogViewModel addLogViewModel) {
            this.addLogViewModel = addLogViewModel;
        }

        @FXML public void initialize() {
                activeImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/technikum_wien/tourplanner/images/pusheen_active.png")));
                inactiveImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/at/technikum_wien/tourplanner/images/pusheen_inactive.png")));

                //Total Time
                initializeDurationInputs();
                //Bind all
                bindBidirectional();
                dateTextField.setOnMouseClicked(e -> hiddenDatePicker.show());

                difficultyComboBox.getItems().addAll("Easy", "Moderate", "Hard");

                addLogViewModel.selectedTourLogProperty().addListener((obs, oldLog, newLog) -> {
                    if (newLog != null) {
                        addLogButton.setText("Update");
                        titleLabel.setText("EDIT LOG");
                    } else {
                        addLogButton.setText("Add Log");
                        titleLabel.setText(ADD_LOG_TITLE);
                    }
                });
                //when date is selected- update ViewModel
                hiddenDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
                    if (newDate != null) {
                        addLogViewModel.dateProperty().set(formatter.format(newDate));
                    }
                });

                pusheenViews = new ImageView[] { pusheen1, pusheen2, pusheen3, pusheen4, pusheen5 };
                updatePusheenImages(0);
                //click listeners
                for (int i = 0; i < pusheenViews.length; i++) {
                    final int ratingValue = i + 1;
                    pusheenViews[i].setOnMouseClicked(e -> {
                        addLogViewModel.ratingProperty().set(String.valueOf(ratingValue));
                        ratingTextField.setText(String.valueOf(ratingValue));
                        updatePusheenImages(ratingValue);
                    });
                }
                //update image view when rating changes
                addLogViewModel.ratingProperty().addListener((obs3, oldVal, newVal) -> {
                    try {
                        int rating = Integer.parseInt(newVal);
                        updatePusheenImages(rating);
                    } catch (NumberFormatException e) {
                        updatePusheenImages(0);
                    }
                });

        }

        private void updatePusheenImages(int rating) {
            for (int i = 0; i < pusheenViews.length; i++) {
                pusheenViews[i].setImage(i < rating ? activeImg : inactiveImg);
            }
        }

        @FXML private void handleAddOrUpdate() {
            if (addLogViewModel.getSelectedTourLog() != null) {
                addLogViewModel.updateLog();
                addLogViewModel.setSelectedTourLog(null);
                addLogButton.setText("Add Log");
                titleLabel.setText(ADD_LOG_TITLE);
                clearForm();
            }else {
                handleAddLog();
            }
        }
        private void handleAddLog() {
            System.out.println("Before addLogAsync:");
            System.out.println("DAYS in VM: " + addLogViewModel.durationDaysProperty().get());
            System.out.println("HOURS in VM: " + addLogViewModel.durationHoursProperty().get());
            System.out.println("MINUTES in VM: " + addLogViewModel.durationMinutesProperty().get());

            resetFieldStyles();
            addLogViewModel.addLogAsync().thenAccept(success -> {
                Platform.runLater(() -> {
                    if (!success) {
                        highlightInvalidFields();
                    }
                });
            });
        }

        private void resetFieldStyles() {
            dateTextField.setStyle(ORIGINAL_STYLE);
            distanceTextField.setStyle(ORIGINAL_STYLE);
            commentTextArea.setStyle(ORIGINAL_STYLE);
            difficultyComboBox.setStyle(ORIGINAL_STYLE);
            ratingTextField.setStyle(ORIGINAL_STYLE);
        }

        private void clearForm() {
            System.out.println("calls clearForm");
            addLogViewModel.ratingProperty().set("");
            updatePusheenImages(0); // visually reset
            addLogViewModel.dateProperty().set("");

            addLogViewModel.durationDaysProperty().set(0);
            addLogViewModel.durationHoursProperty().set(0);
            addLogViewModel.durationMinutesProperty().set(0);

            addLogViewModel.distanceProperty().set("");
            addLogViewModel.commentProperty().set("");
            addLogViewModel.difficultyProperty().set(null); // for ComboBox
        }

        private void highlightInvalidFields() {
            highlightField(dateTextField, addLogViewModel.dateProperty().get());
            highlightField(distanceTextField, addLogViewModel.distanceProperty().get());
            highlightField(commentTextArea, addLogViewModel.commentProperty().get());
            highlightField(difficultyComboBox, addLogViewModel.difficultyProperty().get());
            highlightField(ratingTextField, addLogViewModel.ratingProperty().get());
        }

        private void highlightField(javafx.scene.control.Control field, Object value) {
            boolean invalid;

            if (value == null) {
                invalid = true;
            } else if (value instanceof String str) {
                invalid = str.trim().isEmpty();
            } else if (value instanceof Double dbl) {
                invalid = dbl.isNaN();
            } else {
                invalid = true;
            }

            if (invalid) {
                field.setStyle(ORIGINAL_STYLE + "-fx-border-color: red;");
            } else {
                field.setStyle(ORIGINAL_STYLE);
            }
        }

        private void initializeDurationInputs() {
            daysSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0));
            hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
            minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        }

        private void bindBidirectional() {
            ratingTextField.textProperty().bindBidirectional(addLogViewModel.ratingProperty());
            dateTextField.textProperty().bindBidirectional(addLogViewModel.dateProperty());
            bindSpinnerToProperty(daysSpinner, addLogViewModel.durationDaysProperty());
            bindSpinnerToProperty(hoursSpinner, addLogViewModel.durationHoursProperty());
            bindSpinnerToProperty(minutesSpinner, addLogViewModel.durationMinutesProperty());
            distanceTextField.textProperty().bindBidirectional(addLogViewModel.distanceProperty());
            commentTextArea.textProperty().bindBidirectional(addLogViewModel.commentProperty());
            difficultyComboBox.valueProperty().bindBidirectional(addLogViewModel.difficultyProperty());
        }

        private void bindSpinnerToProperty(Spinner<Integer> spinner, IntegerProperty property) {
            // Spinner → ViewModel
            spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    property.set(newVal);
                }
            });

            // ViewModel → Spinner
            property.addListener((obs, oldVal, newVal) -> {
                if (newVal != null && !newVal.equals(spinner.getValue())) {
                    spinner.getValueFactory().setValue(newVal.intValue());
                }
            });
        }

    }
