package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HomepageMediator;
import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourDetailsViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;

public class ControllerFactory {
    //ToDO maybe should not be here, and all logic in homapageviewmodel??
    private final HomepageMediator homepageMediatorViewModel;
    private final NewTourViewModel newTourViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourTableViewModel tourTableViewModel;

    public ControllerFactory() {
        homepageMediatorViewModel = new HomepageMediator();
        newTourViewModel = new NewTourViewModel(homepageMediatorViewModel);
        tourTableViewModel = new TourTableViewModel(homepageMediatorViewModel);
        tourDetailsViewModel = new TourDetailsViewModel(homepageMediatorViewModel);
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomepageController.class) {
            return new HomepageController(homepageMediatorViewModel);
        }else if (controllerClass == HeaderController.class) {
            return new HeaderController();
        } else if (controllerClass == TourTableController.class) {
            return new TourTableController(tourTableViewModel);
        }else if (controllerClass == NewTourController.class) {
            return new NewTourController(newTourViewModel);
        }else if (controllerClass == TourDetailsController.class) {
            return new TourDetailsController(tourDetailsViewModel);
        }
        else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }

    // Singleton-Pattern with early-binding
    private static final ControllerFactory instance = new ControllerFactory();
    public static ControllerFactory getInstance() {return instance;}
}
