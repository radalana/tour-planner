package at.technikum_wien.tourplanner.model;

import at.technikum_wien.tourplanner.dto.TourLogDTO;
import javafx.beans.property.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TourLog {
    private StringProperty dateTime = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private IntegerProperty difficulty = new SimpleIntegerProperty();
    private StringProperty totalDistance= new SimpleStringProperty();
    private StringProperty totalTime= new SimpleStringProperty();
    private StringProperty rating = new SimpleStringProperty();

    public TourLog(String dateTime, String comment, Integer difficulty, String totalDistance, String totalTime, String rating) {
        this.dateTime = new SimpleStringProperty(dateTime);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.totalDistance = new SimpleStringProperty(totalDistance);
        this.totalTime = new SimpleStringProperty(totalTime);
        this.rating = new SimpleStringProperty(rating);
    }
    public StringProperty ratingProperty() {return this.rating;}
    public StringProperty dateTimeProperty() {return this.dateTime;}
    public StringProperty commentProperty() {return this.comment;}
    public IntegerProperty difficultyProperty() {return this.difficulty;}
    public StringProperty totalDistanceProperty() {return this.totalDistance;}
    public StringProperty totalTimeProperty() {return this.totalTime;}


    //for table view in Controller
    public String getDateTime() {return dateTime.get();}
    public String getComment() {return comment.get();}
    public int getDifficulty() {return difficulty.get();}
    public String getTotalDistance() { return totalDistance.get();}
    public String getTotalTime() {return totalTime.get();}
    public String getRating() {return rating.get();}

    public void setDateTime(String dateTime) {this.dateTime.set(dateTime);}
    public void setComment(String comment) {this.comment.set(comment);}
    public void setDifficulty(int difficulty) {this.difficulty.set(difficulty);}
    public void setTotalDistance(String totalDistance) {this.totalDistance.set(totalDistance);}
    public void setTotalTime(String totalTime) {this.totalTime.set(totalTime);}
    public void setRating(String rating) {this.rating.set(rating);}

    @Override
    public String toString() {
        return "TourLog {" +
                "dateTime='" + getDateTime() + '\'' +
                ", comment='" + getComment() + '\'' +
                ", difficulty=" + getDifficulty() +
                ", totalDistance='" + getTotalDistance() + '\'' +
                ", totalTime='" + getTotalTime() + '\'' +
                ", rating='" + getRating() + '\'' +
                '}';
    }

    //From Model to Data
    public TourLogDTO toDTO() throws NullPointerException {
        TourLogDTO tourLogDTO = new TourLogDTO();
        tourLogDTO.setDateTime(getDateTime());
        tourLogDTO.setComment(getComment());
        tourLogDTO.setDifficulty(getDifficulty());

        double totalDistance = Double.parseDouble(getTotalDistance());
        tourLogDTO.setTotalDistance(totalDistance);
        double totalTime = Double.parseDouble(getTotalTime());
        tourLogDTO.setTotalDuration(totalTime);
        int rating = Integer.parseInt(getRating());
        tourLogDTO.setRating(rating);
        return tourLogDTO;
    }

    //From Data to Model
    public static TourLog fromDTO(TourLogDTO tourLogDTO) {
        TourLog tourLog = new TourLog();
        tourLog.setDateTime(tourLogDTO.getDateTime());
        tourLog.setComment(tourLogDTO.getComment());
        tourLog.setDifficulty(tourLogDTO.getDifficulty());
        tourLog.setTotalDistance(String.valueOf(tourLogDTO.getTotalDistance()));
        tourLog.setTotalTime(String.valueOf(tourLogDTO.getTotalDuration()));
        tourLog.setRating(String.valueOf(tourLogDTO.getRating()));
        return tourLog;
    }
}
