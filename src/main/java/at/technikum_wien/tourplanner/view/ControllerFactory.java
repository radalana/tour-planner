package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.AppMediatorViewModel;
import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;
import at.technikum_wien.tourplanner.viewmodel.NewTourViewModel;
import at.technikum_wien.tourplanner.viewmodel.TourTableViewModel;

public class ControllerFactory {
    private final AppMediatorViewModel mediatorViewModel;
    //ToDO maybe should not be here, and all logic in homapageviewmodel??
    private final HomepageViewModel homepageViewModel;

    public ControllerFactory() {
        mediatorViewModel = new AppMediatorViewModel();
        homepageViewModel = new HomepageViewModel(
                mediatorViewModel.getNewTourViewModel(),
                mediatorViewModel.getTourTableViewModel()
        );
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomepageController.class) {
            return new HomepageController(homepageViewModel);
        }else if (controllerClass == HeaderController.class) {
            return new HeaderController();
        } else if (controllerClass == TourTableController.class) {
            return new TourTableController(mediatorViewModel.getTourTableViewModel());
        }else if (controllerClass == NewTourController.class) {
            return new NewTourController(mediatorViewModel.getNewTourViewModel());
        }else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }

    // Singleton-Pattern with early-binding
    private static final ControllerFactory instance = new ControllerFactory();
    public static ControllerFactory getInstance() {return instance;}
}
