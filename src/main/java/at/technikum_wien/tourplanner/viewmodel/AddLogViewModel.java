package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public class AddLogViewModel {
    private final ObjectProperty<Tour> selectedTour;
    private final MainViewModel mainViewModel;
    @Getter
    private TourLog selectedTourLog = null;
    //TODO here?
    //values from form
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

    public AddLogViewModel(MainViewModel mainViewModel){
        this.selectedTour = mainViewModel.getSelectedTour();
        this.mainViewModel = mainViewModel;

        mainViewModel.selectedLogProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                rating.set(newValue.getRating());
                date.set(newValue.getDateTime());
                duration.set(newValue.getTotalTime());
                distance.set(newValue.getTotalDistance());
                difficulty.set(newValue.getDifficulty());
                comment.set(newValue.getComment());
            }
        });
    }
    public boolean addLog() {
        if (!validateFields()) {
            return false;
        }
        TourLog newLog = new TourLog(
                date.get(),
                comment.get(),
                difficulty.get(),
                distance.get(),
                duration.get(),
                rating.get()
        );
        selectedTour.get().getLogs().add(newLog);

        date.set("");
        comment.set("");
        difficulty.set("");
        distance.set("");
        duration.set("");
        rating.set("");
        return true;
    }
    public void updateLog() {
        selectedTourLog = mainViewModel.getSelectedLog();

        if (selectedTourLog != null) {
            selectedTourLog.setRating(rating.get());
            selectedTourLog.setDateTime(date.get());
            selectedTourLog.setTotalTime(duration.get());
            selectedTourLog.setTotalDistance(distance.get());
            selectedTourLog.setDifficulty(difficulty.get());
            selectedTourLog.setComment(comment.get());
        }
    }
    public void setSelectedTourLog(TourLog tourLog) {
        mainViewModel.setSelectedLog(tourLog);
    }

    private boolean validateFields() {
        return true;
    }

}
