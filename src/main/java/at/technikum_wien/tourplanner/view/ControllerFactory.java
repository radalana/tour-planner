package at.technikum_wien.tourplanner.view;

import at.technikum_wien.tourplanner.viewmodel.HomepageViewModel;

public class ControllerFactory {
    private final HomepageViewModel homepageViewModel;


    public ControllerFactory() {
        homepageViewModel = new HomepageViewModel();
    }

    //Factory-Method Pattern
    public Object create(Class<?> controllerClass) {
        //TODO if (controllerClass == HomepageViewModel.class) {
            return new HomepageController(homepageViewModel);
    }

    // Singleton-Pattern with early-binding
    private static final ControllerFactory instance = new ControllerFactory();
    public static ControllerFactory getInstance() {return instance;}
}
