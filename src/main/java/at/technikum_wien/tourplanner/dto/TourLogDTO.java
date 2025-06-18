package at.technikum_wien.tourplanner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourLogDTO {
    private String dateTime;
    private String comment;
    private int difficulty;
    private double totalDistance;
    private double totalDuration;
    private int rating;
}
