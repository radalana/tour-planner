package at.technikum_wien.tourplanner.dto;

import at.technikum_wien.tourplanner.model.Tour;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TourDTO {
    private Long id;
    private String tourName;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String transportType;
    private double distance;
    private double estimatedTime;
    private int popularity;
    private double childFriendliness;
    public List<TourLogDTO> tourLogs;


}
