package at.technikum_wien.tourplanner.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourLog {
    //TODO
    private ObjectProperty<Tour> tour = new SimpleObjectProperty<>();
    private StringProperty dateTime = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private StringProperty difficulty = new SimpleStringProperty();
    private StringProperty totalDistance= new SimpleStringProperty();
    private StringProperty totalTime= new SimpleStringProperty();
    private StringProperty rating = new SimpleStringProperty();

    public TourLog(Tour tour, String dateTime, String comment, String difficulty, String totalDistance, String totalTime, String rating) {
        this.tour = new SimpleObjectProperty<>(tour); //i'm not sure
        this.dateTime = new SimpleStringProperty(dateTime);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleStringProperty(difficulty);
        this.totalDistance = new SimpleStringProperty(totalDistance);
        this.totalTime = new SimpleStringProperty(totalTime);
        this.rating = new SimpleStringProperty(rating);
    }

    //for table view in Controller
    public String getDateTime() {return dateTime.get();}
    public void setDateTime(String dateTime) {this.dateTime.set(dateTime);}
    public String getComment() {return comment.get();}
    public void setComment(String comment) {this.comment.set(comment);}
    public String getDifficulty() {return difficulty.get();}
}
