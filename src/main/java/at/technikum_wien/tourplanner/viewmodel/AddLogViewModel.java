package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.TourLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddLogViewModel {
    private final HomepageMediator homepageMediator;
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
        this.homepageMediator = homepageMediator;
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
        homepageMediator.addLog(newLog);
        return true;
    }

    private boolean validateFields() {
        return true;
    }
}
