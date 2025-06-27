package at.technikum_wien.tourplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TourUpdateDTO {
    private String tourName;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String transportType;
    private double distance;
    private double estimatedTime;

    public TourUpdateDTO(String tourName, String description, String fromLocation, String toLocation, String transportType) {
        this.tourName = tourName;
        this.description = description;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.transportType = transportType;
    }
}
