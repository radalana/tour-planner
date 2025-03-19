package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppMediatorViewModel;

public class HomepageViewModel {
    //TODO header, table, button_new_tour
    private final NewTourViewModel newTourViewModel;
    private final TourTableViewModel tourTableViewModel;
    public HomepageViewModel(NewTourViewModel newTourViewModel, TourTableViewModel tourTableViewModel) {
        this.newTourViewModel = newTourViewModel;
        this.tourTableViewModel = tourTableViewModel;
    }
}
