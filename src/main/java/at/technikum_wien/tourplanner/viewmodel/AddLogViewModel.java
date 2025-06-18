package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.httpClient.TourLogService;
import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.List;

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
        this.selectedTour = mainViewModel.getSelectedTour();
        this.mainViewModel = mainViewModel;
        this.tourLogService = tourLogService;

        mainViewModel.selectedLogProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                rating.set(newValue.getRating());
                date.set(newValue.getDateTime());
                duration.set(newValue.getTotalTime());
                distance.set(newValue.getTotalDistance());
                comment.set(newValue.getComment());
                setDifficultyFromNumeric(newValue.getDifficulty());
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

    public boolean addLog() {
        //System.out.println("[AddLogViewModel addLog]: selected tour: " + selectedTour.get());
        if (!validateFields()) {
            //System.out.println("[AddLogViewModel addLog] Invalid fields");
            return false;
        }
        //System.out.println("[AddLogViewModel addLog] Validating fields");

        TourLog newLog = new TourLog(
                date.get(),
                comment.get(),
                getNumericDifficulty(),
                distance.get(),
                duration.get(),
                rating.get()
        );

        ObservableList<TourLog> tourLogs = selectedTour.get().getObservableLogs();
        tourLogs.add(newLog);
        clearForm();
        return true;
    }
    public void updateLog() {
        selectedTourLog = mainViewModel.getSelectedLog();

        if (selectedTourLog != null) {
            selectedTourLog.setRating(rating.get());
            selectedTourLog.setDateTime(date.get());
            selectedTourLog.setTotalTime(duration.get());
            selectedTourLog.setTotalDistance(distance.get());
            selectedTourLog.setDifficulty(getNumericDifficulty());
            selectedTourLog.setComment(comment.get());
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
