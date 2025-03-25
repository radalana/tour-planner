package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.Mediator;
import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;

public class ControllerFactory {
    private final Mediator eventAggregator;
    //ToDO maybe should not be here, and all logic in homapageviewmodel??
    private final HomepageViewModel homepageViewModel;

    public ControllerFactory() {
        eventAggregator = new Mediator();
        homepageViewModel = new HomepageViewModel(
                //TODO review maybe viewModels dont needed anymore here
                eventAggregator.getNewTourViewModel(),
                eventAggregator.getTourTableViewModel(),
                eventAggregator.getTourDetailsViewModel(),
                eventAggregator
        );
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomepageController.class) {
            return new HomepageController(homepageViewModel);
        }else if (controllerClass == HeaderController.class) {
            return new HeaderController();
        } else if (controllerClass == TourTableController.class) {
            return new TourTableController(eventAggregator.getTourTableViewModel());
        }else if (controllerClass == NewTourController.class) {
            return new NewTourController(eventAggregator.getNewTourViewModel());
        }else if (controllerClass == TourDetailsController.class) {
            return new TourDetailsController(eventAggregator.getTourDetailsViewModel());
        }
        else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }

    // Singleton-Pattern with early-binding
    private static final ControllerFactory instance = new ControllerFactory();
    public static ControllerFactory getInstance() {return instance;}
}
