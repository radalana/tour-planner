package at.technikum_wien.tourplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourDTO {
    private Long id;
    private String tourName;
    private String description;
    private String fromLocation;
    private String toLocation;
    private String transportType;
    private double distance;
    private long estimatedTime;
    private int popularity;
    private double childFriendliness;

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", tourName='" + tourName + '\'' +
                ", description='" + description + '\'' +
                ", fromLocation='" + fromLocation + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", transportType='" + transportType + '\'' +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                ", popularity=" + popularity +
                ", childFriendliness=" + childFriendliness +
                '}';
    }
}
