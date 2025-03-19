package at.technikum_wien.tourplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tour {
    private String name;
    private String transportType;
    private String from;
    private String to;
    private double distance;
    private String estimatedTime;

    //temporary
    public Tour(String name) {
        this.name = name;
    }
}