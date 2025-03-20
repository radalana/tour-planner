package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class AppEventAggregator {
    //private final ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final Map<Class<? extends AppEvent>, List<Consumer<AppEvent>>> listeners = new HashMap<>();
    public <T extends AppEvent> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add((Consumer<AppEvent>) listener);
    }

    public void publish(AppEvent event) {
        List<Consumer<AppEvent>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<AppEvent> listener : eventListeners) {
                listener.accept(event);
            }
        }
    }

    private final TourTableViewModel tourTableViewModel;
    private final NewTourViewModel newTourViewModel;

    public AppEventAggregator() {
        this.tourTableViewModel = new TourTableViewModel(this);
        this.newTourViewModel = new NewTourViewModel(this);
    }



}
