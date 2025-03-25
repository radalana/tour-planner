package at.technikum_wien.tourplanner.events;

import at.technikum_wien.tourplanner.model.Tour;
import lombok.Getter;

@Getter
public class TourSelectedEvent implements AppEvent{
    private final Tour selectedTour;
    public TourSelectedEvent(Tour selectedTour) {
        this.selectedTour = selectedTour;
    }
}
