package at.technikum_wien.tourplanner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TourLogDTO {
    private Long id;
    private String dateTime;
    private String comment;
    private int difficulty;
    //TODO change to string HH:mm
    private double totalDistance;
    private double totalDuration;
    private int rating;
}
