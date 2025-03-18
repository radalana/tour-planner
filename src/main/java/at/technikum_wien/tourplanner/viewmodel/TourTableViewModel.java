package at.technikum_wien.tourplanner.viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourTableViewModel {
    private final ObservableList<HighscoreEntry> data =
            FXCollections.observableArrayList(
                    new HighscoreEntry("daniel", "infinite"),
                    new HighscoreEntry("not daniel", "few")
            );
}
