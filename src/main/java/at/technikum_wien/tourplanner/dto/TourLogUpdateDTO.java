package at.technikum_wien.tourplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TourLogUpdateDTO {
    private String date;
    private String comment;
    private int difficulty;
    private double totalDistance;
    private double totalDuration;
    private int rating;
}
