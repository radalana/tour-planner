package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.Mediator;

public class TourDetailsViewModel {
    private final Mediator eventAggregator;
    public TourDetailsViewModel(Mediator eventAggregator) {
        this.eventAggregator = eventAggregator;
    }
}
