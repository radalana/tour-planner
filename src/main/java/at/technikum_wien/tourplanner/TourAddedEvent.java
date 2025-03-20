package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.model.Tour;

public class TourAddedEvent implements AppEvent {
    private final Tour newTour;
    public TourAddedEvent(Tour newTour) {
        this.newTour = newTour;
    }
    public Tour getNewTour() {
        return newTour;
    }
}
