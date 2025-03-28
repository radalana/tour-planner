package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.model.TourLog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddLogViewModel {
    private final ObjectProperty<Tour> selectedTour;
    //TODO here?
    //values from form
    private final StringProperty rating = new SimpleStringProperty();
    private final StringProperty date = new SimpleStringProperty();
    private final StringProperty duration = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final StringProperty difficulty = new SimpleStringProperty();
    //for binding

    public StringProperty ratingProperty() {return rating;}
    public StringProperty dateProperty() {return date;}
    public StringProperty durationProperty() {return duration;}
    public StringProperty distanceProperty() {return distance;}
    public StringProperty commentProperty() {return comment;}
    public StringProperty difficultyProperty() {return difficulty;}

    public AddLogViewModel(HomepageMediator homepageMediator){
        this.selectedTour = homepageMediator.getSelectedTour();
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
        return true;
    }

    private boolean validateFields() {
        return true;
    }
}
