package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.*;

public class ControllerFactory {
    private final ViewModelFactory viewModelFactory;

    public ControllerFactory(ViewModelFactory viewModelFactory) {
       this.viewModelFactory = viewModelFactory;
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        if (controllerClass == HomepageController.class) {
            return new HomepageController(viewModelFactory.getMainViewModel());
        }else if (controllerClass == HeaderController.class) {
            return new HeaderController(viewModelFactory.getHeaderViewModel());
        } else if (controllerClass == TourTableController.class) {
            return new TourTableController(viewModelFactory.getTourTableViewModel());
        }else if (controllerClass == NewTourController.class) {
            return new NewTourController(viewModelFactory.getNewTourViewModel());
        }else if (controllerClass == TourDetailsController.class) {
            return new TourDetailsController(viewModelFactory.getTourDetailsViewModel());
        } else if (controllerClass == TourLogController.class) {
            return new TourLogController(viewModelFactory.getTourLogViewModel());
        } else if (controllerClass == AddLogController.class) {
            return new AddLogController(viewModelFactory.getAddLogViewModel());
        }
        else {
            throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
        }
    }
}
