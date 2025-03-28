package at.technikum_wien.tourplanner.viewmodel;

import at.technikum_wien.tourplanner.model.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class TourLogViewModel {
    private final HomepageMediator homepageMediator;
    public TourLogViewModel(HomepageMediator homepageMediator) {
        this.homepageMediator = homepageMediator;
    }

    public ObservableList<TourLog> getLogs() {
        return homepageMediator.getLogs();
    }



}
