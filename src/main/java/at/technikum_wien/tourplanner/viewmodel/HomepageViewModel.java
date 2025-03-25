package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.Mediator;
import lombok.Getter;

@Getter
public class HomepageViewModel {
    private final Mediator mediator;
    //TODO header, table, button_new_tour
    private final NewTourViewModel newTourViewModel;
    private final TourTableViewModel tourTableViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    public HomepageViewModel(NewTourViewModel newTourViewModel, TourTableViewModel tourTableViewModel, TourDetailsViewModel tourDetailsViewModel, Mediator mediator) {
        this.newTourViewModel = newTourViewModel;
        this.tourTableViewModel = tourTableViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.mediator = mediator;

    }
}
