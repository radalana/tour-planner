package at.technikum_wien.tourplanner.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class Tour {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty from = new SimpleStringProperty();
    private StringProperty to = new SimpleStringProperty();
    private StringProperty transportType = new SimpleStringProperty();//TODO maybe enum
    private DoubleProperty distance = new SimpleDoubleProperty();
    private StringProperty estimatedTime = new SimpleStringProperty();
    private StringProperty routInfo = new SimpleStringProperty();
   @Getter
   private final ObservableList<TourLog> logs = FXCollections.observableArrayList();

    public Tour(String name, String description, String from, String to, String transportType, Double distance, String estimatedTime, String routInfo) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.from = new SimpleStringProperty(from);
        this.to = new SimpleStringProperty(to);
        this.transportType = new SimpleStringProperty(transportType);
        this.distance = new SimpleDoubleProperty(distance);
        this.estimatedTime = new SimpleStringProperty(estimatedTime);
        this.routInfo = new SimpleStringProperty(routInfo);
    }

    //for tableView in Controller
    public String getName() {return name.get();}
    public String getDescription() {return description.get();}
    public String getFrom() {return from.get();}
    public String getTo() {return to.get();}
    public String getTransportType() {return transportType.get();}
    public double getDistance() {return distance.get();}
    public String getEstimatedTime() {return estimatedTime.get();}
    public String getRoutInfo() {return routInfo.get();}

    public void setName(String newName) {name.set(newName);}
    public void setDescription(String newDescription) {description.set(newDescription);}
    public void setFrom(String newFrom) {from.set(newFrom);}
    public void setTo(String newTo) {to.set(newTo);}
    public void setTransportType(String newTransportType) {transportType.set(newTransportType);}
    public void setDistance(double newDistance) {distance.set(newDistance);}
    public void setEstimatedTime(String newEstimatedTime) {estimatedTime.set(newEstimatedTime);}
    public void setRoutInfo(String newRoutInfo) {routInfo.set(newRoutInfo);}


}