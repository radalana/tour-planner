package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppEventAggregator;

public class TourDetailsViewModel {
    private final AppEventAggregator eventAggregator;
    public TourDetailsViewModel(AppEventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;
    }
}
