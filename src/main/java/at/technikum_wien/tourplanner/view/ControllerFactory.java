package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;

public class ControllerFactory {
    private final HomepageViewModel homepageViewModel;
    private final TourTableViewModel tourTableViewModel;


    public ControllerFactory() {
        homepageViewModel = new HomepageViewModel();
        tourTableViewModel = new TourTableViewModel();
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomepageController.class) {
            return new HomepageController(homepageViewModel);
        }else if (controllerClass == TourTableController.class) {
            return new TourTableController(tourTableViewModel);
        }else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }

    }

    // Singleton-Pattern with early-binding
    private static final ControllerFactory instance = new ControllerFactory();
    public static ControllerFactory getInstance() {return instance;}
}
