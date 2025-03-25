package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppEventAggregator;
import lombok.Getter;

@Getter
public class HomepageViewModel {
    private final AppEventAggregator appEventAggregator;
    //TODO header, table, button_new_tour
    private final NewTourViewModel newTourViewModel;
    private final TourTableViewModel tourTableViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    public HomepageViewModel(NewTourViewModel newTourViewModel, TourTableViewModel tourTableViewModel, TourDetailsViewModel tourDetailsViewModel,AppEventAggregator appEventAggregator) {
        this.newTourViewModel = newTourViewModel;
        this.tourTableViewModel = tourTableViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.appEventAggregator = appEventAggregator;

    }
}
