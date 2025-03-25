package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.model.Tour;
import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourDetailsViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class AppEventAggregator {
    //Mediator
    private ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    //для binding подписки
    public ObjectProperty<Tour> selectedTourProperty() {return selectedTour;}
    public ObservableList<Tour> getTours() {return tours;}
    public AppEventAggregator() {
        this.tourTableViewModel = new TourTableViewModel(this);
        this.newTourViewModel = new NewTourViewModel(this);
        this.tourDetailsViewModel = new TourDetailsViewModel(this);

        tours.addAll(new Tour("Vienna Bratislava", "Weekend in Bratislaba", "Vienna", "Bratislaba", "Train", 40.0, "1 hour", "bla-bla"),
                new Tour("Trip in Sibirien", "Urlaub in Russland", "Wien", "Ulan-Use", "Plain", 9000.0, "2 Days", "bla-bla"));
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    private final TourTableViewModel tourTableViewModel;
    private final NewTourViewModel newTourViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;





}
