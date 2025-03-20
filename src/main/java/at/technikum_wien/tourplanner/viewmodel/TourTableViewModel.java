package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.AppEventAggregator;
import at.technikum_wien.tourplanner.TourAddedEvent;
import at.technikum_wien.tourplanner.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class TourTableViewModel {
    private final AppEventAggregator eventAggregator;

    @Getter
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();
    public TourTableViewModel(AppEventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;
        eventAggregator.subscribe(TourAddedEvent.class, event -> {
            tours.add(event.getNewTour()); // ⬅ добавляем в таблицу
        });
    }
}
