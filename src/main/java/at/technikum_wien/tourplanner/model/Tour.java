package at.technikum_wien.tourplanner.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Tour {
    private String name;
    private String transportType;
    private String from;
    private String to;
    private double distance;
    private String estimatedTime;

}
