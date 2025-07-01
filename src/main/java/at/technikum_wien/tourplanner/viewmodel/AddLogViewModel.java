package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourLogUpdateDTO;
import at.technikum_wien.tourplanner.httpClient.TourLogService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.concurrent.CompletableFuture;

public class AddLogViewModel {
    private final TourLogService tourLogService;
    private final ObjectProperty<Tour> selectedTour;
    private final MainViewModel mainViewModel;
    private TourLog selectedTourLog = null;

    private final StringProperty rating = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();

    private final IntegerProperty durationDays = new SimpleIntegerProperty();
    private final IntegerProperty durationHours = new SimpleIntegerProperty();
    private final IntegerProperty durationMinutes = new SimpleIntegerProperty();

    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final StringProperty difficulty = new SimpleStringProperty();

    private final BooleanProperty validForm = new SimpleBooleanProperty(true);


    //for binding
    public ObjectProperty<TourLog> selectedTourLogProperty() {
        return mainViewModel.selectedLogProperty();
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }

    public IntegerProperty durationDaysProperty() {
        return durationDays;
    }

    public IntegerProperty durationHoursProperty() {
        return durationHours;
    }

    public IntegerProperty durationMinutesProperty() {
        return durationMinutes;
    }

    public BooleanProperty validFormProperty() {
        return validForm;
    }

    public AddLogViewModel(MainViewModel mainViewModel, TourLogService tourLogService) {
        // TODO selectedTOUr should not be field of class, bcs object handle a lot of tours
        this.selectedTour = mainViewModel.getSelectedTour();
        this.mainViewModel = mainViewModel;
        this.tourLogService = tourLogService;

        mainViewModel.selectedLogProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("New value of Selected log: " + newValue);
                rating.set(newValue.getRating());
                date.set(newValue.getDate());
                durationDays.set(newValue.getDurationDays());
                durationHours.set(newValue.getDurationHours());
                durationMinutes.set(newValue.getDurationMinutes());
                distance.set(newValue.getTotalDistance());
                comment.set(newValue.getComment());
                setDifficultyFromNumeric(newValue.getDifficulty());
            } else {
                clearForm();
            }
        });

        date.addListener((obs, old, newVal) -> updateFormValidity());
        distance.addListener((obs, old, newVal) -> updateFormValidity());
        comment.addListener((obs, old, newVal) -> updateFormValidity());
        difficulty.addListener((obs, old, newVal) -> updateFormValidity());
        rating.addListener((obs, old, newVal) -> updateFormValidity());

        durationDays.addListener((obs, old, newVal) -> updateFormValidity());
        durationHours.addListener((obs, old, newVal) -> updateFormValidity());
        durationMinutes.addListener((obs, old, newVal) -> updateFormValidity());

        // начальная проверка
        updateFormValidity();
    }

    // convert label to number for backend use
    public int getNumericDifficulty() {
        return switch (difficulty.get().toLowerCase()) {
            case "easy" -> 1;
            case "moderate" -> 2;
            case "hard" -> 3;
            default -> 0;
        };
    }

    // convert number to label for UI display
    public void setDifficultyFromNumeric(double value) {
        switch ((int) value) {
            case 1 -> difficulty.set("Easy");
            case 2 -> difficulty.set("Moderate");
            case 3 -> difficulty.set("Hard");
            default -> difficulty.set("");
        }
    }

    public CompletableFuture<Boolean> addLogAsync() {
        System.out.println("Log added asynchronously");
        Tour tour = selectedTour.get();
        if (!validateFields()) {
            System.out.println("Fields are not valid");
            return CompletableFuture.completedFuture(false);
        }
        TourLog newLog = new TourLog(
                date.get(),
                comment.get(),
                getNumericDifficulty(),
                distance.get(),
                durationDays.get(),
                durationHours.get(),
                durationMinutes.get(),
                rating.get()
        );

        ObservableList<TourLog> tourLogs = tour.getObservableLogs();

        return tourLogService.createLogAsync(newLog, tour.getId()).thenApply(tourLog -> {
            Platform.runLater(() -> {
                tourLogs.add(tourLog);
                clearForm();
            });

            return true;
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                // TODO alert
                System.err.println("Error by creating TourLog: " + ex.getMessage());
            });
            return false;
        });
    }

    public void updateLog() {
        Tour tour = selectedTour.get();
        selectedTourLog = mainViewModel.getSelectedLog();

        if (selectedTourLog != null) {
            double newDistance = Double.parseDouble(distance.get());
            long newDurationInSec = (long) durationDays.get() * 60 * 60 * 24 + durationHours.get() * 60 * 60 + durationMinutes.get() * 60;
            int newRating = Integer.parseInt(rating.get());
            TourLogUpdateDTO editedLogData = new TourLogUpdateDTO(
                    date.get(),
                    comment.get(),
                    getNumericDifficulty(),
                    newDistance,
                    newDurationInSec,
                    newRating
            );
            tourLogService.updateLogAsync(editedLogData, selectedTourLog.getId(), tour.getId())
                    .thenAccept(editedLog -> {
                        Platform.runLater(() -> {
                            selectedTourLog.setId(editedLog.getId());
                            selectedTourLog.setRating(editedLog.getRating());
                            selectedTourLog.setDate(editedLog.getDate());

                            selectedTourLog.setDurationDays(editedLog.getDurationDays());
                            selectedTourLog.setDurationHours(editedLog.getDurationHours());
                            selectedTourLog.setDurationMinutes(editedLog.getDurationMinutes());

                            selectedTourLog.setTotalDistance(editedLog.getTotalDistance());
                            selectedTourLog.setComment(editedLog.getComment());
                            selectedTourLog.setDifficulty(editedLog.getDifficulty());
                        });
                        System.out.println("Tour log updated");
                    }).exceptionally(ex -> {
                        Platform.runLater(() -> {
                            // TODO alert
                            System.err.println("Error by creating TourLog: " + ex.getMessage());
                        });
                        return null;
                    });

        }
    }

    public void setSelectedTourLog(TourLog tourLog) {
        mainViewModel.setSelectedLog(tourLog);
    }

    public TourLog getSelectedTourLog() {
        return mainViewModel.getSelectedLog();
    }

    public void clearForm() {
        rating.set("");
        date.set("");
        durationDays.set(0);
        durationHours.set(0);
        durationMinutes.set(0);
        distance.set("");
        comment.set("");
        difficulty.set("");
    }

    private boolean validateFields() {
        boolean allValid = isValidDate() &&
                isValidDuration() &&
                isValidDistance() &&
                isValidComment() &&
                isValidDifficulty();

        validForm.set(allValid);
        return allValid;
    }

    private boolean isNotEmpty(Object value) {
        if (value == null) {
            return false;
        } else if (value instanceof String str) {
            return !str.trim().isEmpty() && !str.equals("0");
        } else if (value instanceof Number num) {
            return num.doubleValue() != 0.0 && !Double.isNaN(num.doubleValue());
        }
        return true; //for other types the default is true
    }

    private boolean isValidNumber(String value) {
        if (value == null || value.trim().isEmpty()) return false;
        String sanitized = value.trim().replace(",", ".");
        if (sanitized.endsWith(".")) sanitized += "0";
        try {
            Double.parseDouble(sanitized);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidDate() {
        return isNotEmpty(date.get());
    }

    public boolean isValidDuration() {
        return durationDays.get() != 0 || durationHours.get() != 0 || durationMinutes.get() != 0;
    }

    public boolean isValidDistance() {
        return isValidNumber(distance.get());
    }

    public boolean isValidComment() {
        return isNotEmpty(comment.get());
    }

    public boolean isValidDifficulty() {
        return isNotEmpty(difficulty.get());
    }

    public boolean isValidRating() {
        return isNotEmpty(rating.get());
    }

    private void updateFormValidity() {
        validForm.set(
                isValidDate() &&
                        isValidDuration() &&
                        isValidDistance() &&
                        isValidComment() &&
                        isValidDifficulty()
        );
    }

}
