package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.*;

public class ControllerFactory {
    //ToDO maybe should not be here, and all logic in homapageviewmodel??
    private final MainViewModel mainViewModelViewModel;
    private final NewTourViewModel newTourViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourTableViewModel tourTableViewModel;
    private final TourLogViewModel tourLogViewModel;
    private final AddLogViewModel addLogViewModel;

    public ControllerFactory() {
        mainViewModelViewModel = new MainViewModel();
        newTourViewModel = new NewTourViewModel(mainViewModelViewModel);
        tourTableViewModel = new TourTableViewModel(mainViewModelViewModel);
        tourDetailsViewModel = new TourDetailsViewModel(mainViewModelViewModel);
        tourLogViewModel = new TourLogViewModel(mainViewModelViewModel);
        addLogViewModel = new AddLogViewModel(mainViewModelViewModel);
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomepageController.class) {
            return new HomepageController(mainViewModelViewModel);
        }else if (controllerClass == HeaderController.class) {
            return new HeaderController();
        } else if (controllerClass == TourTableController.class) {
            return new TourTableController(tourTableViewModel);
        }else if (controllerClass == NewTourController.class) {
            return new NewTourController(newTourViewModel);
        }else if (controllerClass == TourDetailsController.class) {
            return new TourDetailsController(tourDetailsViewModel);
        } else if (controllerClass == TourLogController.class) {
            return new TourLogController(tourLogViewModel);
        } else if (controllerClass == AddLogController.class) {
            return new AddLogController(addLogViewModel);
        }
        else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }

    // Singleton-Pattern with early-binding
    private static final ControllerFactory instance = new ControllerFactory();
    public static ControllerFactory getInstance() {return instance;}
}
