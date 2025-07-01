package at.technikum_wien.tourplanner.model;

import at.technikum_wien.tourplanner.dto.TourLogDTO;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class TourLog {
    @Getter @Setter
    private Long id;
    private StringProperty date = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();
    private IntegerProperty difficulty = new SimpleIntegerProperty();
    private StringProperty totalDistance= new SimpleStringProperty();
    private IntegerProperty durationDays = new SimpleIntegerProperty();
    private IntegerProperty durationHours = new SimpleIntegerProperty();
    private IntegerProperty durationMinutes = new SimpleIntegerProperty();
    private StringProperty rating = new SimpleStringProperty();

    public TourLog(String date, String comment, Integer difficulty, String totalDistance, int days, int hours, int minutes, String rating) {
        this.date = new SimpleStringProperty(date);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.totalDistance = new SimpleStringProperty(totalDistance);
        this.durationDays = new SimpleIntegerProperty(days);
        this.durationHours = new SimpleIntegerProperty(hours);
        this.durationMinutes = new SimpleIntegerProperty(minutes);
        this.rating = new SimpleStringProperty(rating);
    }
    public StringProperty ratingProperty() {return this.rating;}
    public StringProperty dateTimeProperty() {return this.date;}
    public StringProperty commentProperty() {return this.comment;}
    public IntegerProperty difficultyProperty() {return this.difficulty;}
    public StringProperty totalDistanceProperty() {return this.totalDistance;}
    public IntegerProperty durationDaysProperty() {return this.durationDays;}
    public IntegerProperty durationHoursProperty() {return this.durationHours;}
    public IntegerProperty durationMinutesProperty() {return this.durationMinutes;}


    //for table view in Controller
    public String getDate() {return date.get();}
    public String getComment() {return comment.get();}
    public int getDifficulty() {return difficulty.get();}
    public String getTotalDistance() { return totalDistance.get();}
    //public String getTotalTime() {return totalTime.get();}

    public int getDurationDays() {return durationDays.get();}
    public int getDurationHours() {return durationHours.get();}
    public int getDurationMinutes() {return durationMinutes.get();}

    public String getRating() {return rating.get();}

    public void setDate(String date) {this.date.set(date);}
    public void setComment(String comment) {this.comment.set(comment);}
    public void setDifficulty(int difficulty) {this.difficulty.set(difficulty);}
    public void setTotalDistance(String totalDistance) {this.totalDistance.set(totalDistance);}

    //public void setTotalTime(String totalTime) {this.totalTime.set(totalTime);}
    public void setDurationDays(int durationDays) {this.durationDays.set(durationDays);}
    public void setDurationHours(int durationHours) {this.durationHours.set(durationHours);}
    public void setDurationMinutes(int durationMinutes) {this.durationMinutes.set(durationMinutes);}
    public void setRating(String rating) {this.rating.set(rating);}

    @Override
    public String toString() {
        return "TourLog {" +
                "date='" + getDate() + '\'' +
                ", comment='" + getComment() + '\'' +
                ", difficulty=" + getDifficulty() +
                ", totalDistance='" + getTotalDistance() + '\'' +
                ", totalTime='" + 33 + '\'' +
                ", rating='" + getRating() + '\'' +
                '}';
    }

    //From Model to Data
    public TourLogDTO toDTO() throws NullPointerException {
        TourLogDTO tourLogDTO = new TourLogDTO();
        tourLogDTO.setDate(getDate());
        tourLogDTO.setComment(getComment());
        tourLogDTO.setDifficulty(getDifficulty());

        double totalDistance = Double.parseDouble(getTotalDistance());
        tourLogDTO.setTotalDistance(totalDistance);

        double totalTime = getDurationDays()*86400+ getDurationHours()*3600 + getDurationMinutes()*60;

        tourLogDTO.setTotalDuration(totalTime);
        int rating = Integer.parseInt(getRating());
        tourLogDTO.setRating(rating);
        return tourLogDTO;
    }

    //From Data to Model
    public static TourLog fromDTO(TourLogDTO tourLogDTO) {
        TourLog tourLog = new TourLog();
        tourLog.setId(tourLogDTO.getId());
        tourLog.setDate(tourLogDTO.getDate());
        tourLog.setComment(tourLogDTO.getComment());
        tourLog.setDifficulty(tourLogDTO.getDifficulty());
        tourLog.setTotalDistance(String.valueOf(tourLogDTO.getTotalDistance()));

        //tourLog.setTotalTime(String.valueOf(tourLogDTO.getTotalDuration()));
        int days = 1;
        int hours = 2;
        int minutes = 3;

        tourLog.setDurationDays(days);
        tourLog.setDurationHours(hours);
        tourLog.setDurationMinutes(minutes);

        tourLog.setRating(String.valueOf(tourLogDTO.getRating()));
        return tourLog;
    }
}
