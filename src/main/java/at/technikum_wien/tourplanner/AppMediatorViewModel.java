package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import at.technikum_wien.tourplanner.model.Tour;
import lombok.Getter;

public class AppMediatorViewModel implements Mediator {
    @Getter private final ObservableList<Tour> tours = FXCollections.observableArrayList();

    @Getter private final TourTableViewModel tourTableViewModel;
    @Getter private final NewTourViewModel newTourViewModel;

    public AppMediatorViewModel() {
        this.tourTableViewModel = new TourTableViewModel(this);
        this.newTourViewModel = new NewTourViewModel(this);
    }
    @Override
    public void addTour(Tour tour) {
        tours.add(tour);
        System.out.println("Tour added in ObservableList: " + tour);
    }


}
