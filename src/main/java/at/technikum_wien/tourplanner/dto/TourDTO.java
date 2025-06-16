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
    private String from;
    private String to;
    private String transportType;
    private double distance;
    private double estimatedTime;
    private int popularity;
    private double childFriendliness;
    public List<TourLogDTO> tourLogs;

    //from UI to data
    public TourDTO toDTO() {
        TourDTO dto = new TourDTO();
        dto.tourName = getTourName();
        dto.description = getDescription();
        dto.from = getFrom();
        dto.to = getTo();
        dto.transportType = getTransportType();
        dto.distance = getDistance();
        dto.estimatedTime = getEstimatedTime();
        dto.popularity = getPopularity();
        dto.childFriendliness = getChildFriendliness();
        return dto;
    }
    //From data to UI
    public static Tour fromDTO(TourDTO dto) {
        Tour tour = new Tour();
        tour.setId(dto.getId());
        tour.setTourName(dto.tourName);
        tour.setDescription(dto.description);
        tour.setFrom(dto.from);
        tour.setTo(dto.to);
        tour.setTransportType(dto.transportType);
        tour.setDistance(dto.distance);
        tour.setEstimatedTime(dto.estimatedTime);
        tour.setPopularity(dto.popularity);
        tour.setChildFriendliness(dto.childFriendliness);
        return tour;
    }
}
