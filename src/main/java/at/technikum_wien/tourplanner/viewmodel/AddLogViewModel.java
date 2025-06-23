package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.dto.TourLogUpdateDTO;
import at.technikum_wien.tourplanner.httpClient.TourLogService;
import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AddLogViewModel {
    private final TourLogService tourLogService;
    private final ObjectProperty<Tour> selectedTour;
    private final MainViewModel mainViewModel;
    private TourLog selectedTourLog = null;

    private final StringProperty rating = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty duration = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final StringProperty difficulty = new SimpleStringProperty();
    //for binding
    public ObjectProperty<TourLog> selectedTourLogProperty() {
        return mainViewModel.selectedLogProperty();
    }
    public StringProperty ratingProperty() {return rating;}
    public StringProperty dateProperty() {return date;}
    public StringProperty durationProperty() {return duration;}
    public StringProperty distanceProperty() {return distance;}
    public StringProperty commentProperty() {return comment;}
    public StringProperty difficultyProperty() {return difficulty;}

    public AddLogViewModel(MainViewModel mainViewModel, TourLogService tourLogService) {
        // TODO selectedTOUr should not be field of class, bcs object handle a lot of tours
        this.selectedTour = mainViewModel.getSelectedTour();
        this.mainViewModel = mainViewModel;
        this.tourLogService = tourLogService;

        mainViewModel.selectedLogProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                rating.set(newValue.getRating());
                date.set(newValue.getDate());
                duration.set(newValue.getTotalTime());
                distance.set(newValue.getTotalDistance());
                comment.set(newValue.getComment());
                setDifficultyFromNumeric(newValue.getDifficulty());
            }
        });

        mainViewModel.isLogDeletedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                clearForm();
            }
        });
    }

    // Convert label to number for backend use
    public int getNumericDifficulty() {
        return switch (difficulty.get().toLowerCase()) {
            case "easy" -> 1;
            case "moderate" -> 2;
            case "hard" -> 3;
            default -> 0;
        };
    }

    // Convert number to label for UI display
    public void setDifficultyFromNumeric(double value) {
        switch ((int) value) {
            case 1 -> difficulty.set("Easy");
            case 2 -> difficulty.set("Moderate");
            case 3 -> difficulty.set("Hard");
            default -> difficulty.set("");
        }
    }

    public CompletableFuture<Boolean> addLogAsync() {
        Tour tour = selectedTour.get();
        //System.out.println("[AddLogViewModel addLog]: selected tour: " + selectedTour.get());
        if (!validateFields()) {
            //System.out.println("[AddLogViewModel addLog] Invalid fields");
            return CompletableFuture.completedFuture(false);
        }
        TourLog newLog = new TourLog(
                date.get(),
                comment.get(),
                getNumericDifficulty(),
                distance.get(),
                duration.get(),
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
            double newDuration = Double.parseDouble(duration.get());
            int newRating = Integer.parseInt(rating.get());
            TourLogUpdateDTO editedLogData = new TourLogUpdateDTO(
                    date.get(),
                    comment.get(),
                    getNumericDifficulty(),
                    newDistance,
                    newDuration,
                    newRating
            );
            tourLogService.updateLogAsync(editedLogData, selectedTourLog.getId(), tour.getId())
                    .thenAccept(editedLog -> {
                        Platform.runLater(() -> {
                            selectedTourLog.setId(editedLog.getId());
                            selectedTourLog.setRating(editedLog.getRating());
                            selectedTourLog.setDate(editedLog.getDate());
                            selectedTourLog.setTotalTime(editedLog.getTotalTime());
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
        duration.set("");
        distance.set("");
        comment.set("");
        difficulty.set("");
    }
    private boolean validateFields() {
        return isNotEmpty(date.get()) &&
                isNotEmpty(duration.get()) &&
                isNotEmpty(distance.get()) &&
                isNotEmpty(comment.get()) &&
                isNotEmpty(difficulty.get());
    }

    private boolean isNotEmpty(Object value) {
        if (value == null) {
            return false;
        } else if (value instanceof String str) {
            return !str.trim().isEmpty() && !str.equals("0");
        } else if (value instanceof Number num) {
            return num.doubleValue() != 0.0 && !Double.isNaN(num.doubleValue());
        }
        return true; // для других типов по умолчанию true, можно уточнить при необходимости
    }
}
