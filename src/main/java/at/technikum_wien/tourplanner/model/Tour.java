package at.technikum_wien.tourplanner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
public class Tour {
    private StringProperty name = new SimpleStringProperty();
    private String transportType;
    private String from;
    private String to;
    private double distance;
    private String estimatedTime;

    //temporary
    public Tour(String name) {
        this.name = new SimpleStringProperty(name);
    }
    public String getName() {
        return name != null ? name.get() : "<empty>";}
    public StringProperty nameProperty() { return name; } //for TableView
    public void setName(String name) { this.name.set(name); }//because of binding
}