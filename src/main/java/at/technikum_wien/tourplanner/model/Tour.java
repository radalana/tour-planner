package at.technikum_wien.tourplanner.model;

import at.technikum_wien.tourplanner.dto.TourDTO;
import at.technikum_wien.tourplanner.utils.TimeConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tour {
    @Getter @Setter
    private Long id; //for correct serialization
    private StringProperty tourName = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    private StringProperty from = new SimpleStringProperty();
    private StringProperty to = new SimpleStringProperty();
    private StringProperty transportType = new SimpleStringProperty();//TODO maybe enum
    private DoubleProperty distance = new SimpleDoubleProperty();
    private StringProperty estimatedTime = new SimpleStringProperty();
    private StringProperty routInfo = new SimpleStringProperty();
    private IntegerProperty popularity = new SimpleIntegerProperty();
    private DoubleProperty childFriendliness = new SimpleDoubleProperty();

    // tourlog list access
    // backend logs list (for Jackson)
    //@Getter
    @JsonProperty("tourLogs")
    private List<TourLog> logs = new ArrayList<>();

   @Getter
   private final ObservableList<TourLog> observableLogs = FXCollections.observableArrayList();
    //For Create from UI
    public Tour(String tourName, String description, String from, String to, String transportType, Double distance, String estimatedTime, String routInfo) {
        this.tourName = new SimpleStringProperty(tourName);
        this.description = new SimpleStringProperty(description);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.transportType = new SimpleStringProperty(transportType);
        this.distance = new SimpleDoubleProperty(distance);
        this.estimatedTime = new SimpleStringProperty(estimatedTime);
        this.routInfo = new SimpleStringProperty(routInfo);
    }

    //For Create from POST response (added Id field/not used in UI)
    public Tour(long id, String tourName, String description, String from, String to, String transportType, Double distance, String estimatedTime, String routInfo, Integer popularity) {
        this.id = id;
        this.tourName = new SimpleStringProperty(tourName);
        this.description = new SimpleStringProperty(description);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.transportType = new SimpleStringProperty(transportType);
        this.distance = new SimpleDoubleProperty(distance);
        this.estimatedTime = new SimpleStringProperty(estimatedTime);
        this.routInfo = new SimpleStringProperty(routInfo);
        this.popularity = new SimpleIntegerProperty(popularity);
    }

    //for tableView in Controller
    public String getTourName() {return tourName.get();}
    public String getDescription() {return description.get();}
    public String getFrom() {return from.get();}
    public String getTo() {return to.get();}
    public String getTransportType() {return transportType.get();}
    public double getDistance() {return distance.get();}
    public String getEstimatedTime() {return estimatedTime.get();}
    public int getPopularity() {return popularity.get();}
    public String getRoutInfo() {return routInfo.get();}


    public void setTourName(String newName) {
        tourName.set(newName);}
    public void setDescription(String newDescription) {description.set(newDescription);}
    public void setFrom(String newFrom) {from.set(newFrom);}
    public void setTo(String newTo) {to.set(newTo);}
    public void setTransportType(String newTransportType) {transportType.set(newTransportType);}
    public void setDistance(double newDistance) {distance.set(newDistance);}
    public void setEstimatedTime(String newEstimatedTime) {estimatedTime.set(newEstimatedTime);}
    public void setRoutInfo(String newRoutInfo) {routInfo.set(newRoutInfo);}
    public void setPopularity(int newPopularity) {popularity.set(newPopularity);}
    public void setChildFriendliness(double ChildFriendliness) {childFriendliness.set(ChildFriendliness);}

    //for binding
    public StringProperty tourNameProperty() {
        return tourName;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public StringProperty estimatedTimeProperty() {
        return estimatedTime;
    }

    public StringProperty routInfoProperty() {
        return routInfo;
    }

    //from UI to data
    public TourDTO toDTO() {
        TourDTO dto = new TourDTO();
        dto.setTourName(getTourName());
        dto.setDescription(getDescription());
        dto.setFromLocation(getFrom());
        dto.setToLocation(getTo());
        dto.setTransportType(getTransportType());
        dto.setDistance(getDistance());
        String estimatedTimeString = getEstimatedTime();
        dto.setEstimatedTime(TimeConverter.fromStringToDouble(estimatedTimeString));
        return dto;
    }
    //From data to UI
    public static Tour fromDTO(TourDTO dto) {
        Tour tour = new Tour();
        tour.setId(dto.getId());
        tour.setTourName(dto.getTourName());
        tour.setDescription(dto.getDescription());
        tour.setFrom(dto.getFromLocation());
        tour.setTo(dto.getToLocation());
        tour.setTransportType(dto.getTransportType());
        tour.setDistance(dto.getDistance());
        Double totalSeconds = dto.getEstimatedTime();

        tour.setEstimatedTime(TimeConverter.fromDoubleToString(totalSeconds));
        tour.setPopularity(dto.getPopularity());
        tour.setChildFriendliness(dto.getChildFriendliness());
        return tour;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", tourName='" + getTourName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", from='" + getFrom() + '\'' +
                ", to='" + getTo() + '\'' +
                ", transportType='" + getTransportType() + '\'' +
                ", distance=" + getDistance() +
                ", estimatedTime=" + getEstimatedTime() +
                ", routInfo='" + getRoutInfo() + '\'' +
                ", popularity=" + getPopularity() +
                ", childFriendliness=" + childFriendliness.get() +
                '}';
    }

}