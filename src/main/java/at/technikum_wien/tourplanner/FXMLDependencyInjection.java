package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.view.ControllerFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * FXMLLoader with Dependency-Injection
 * Copied from https://git.technikum-wien.at/swen/swen2/java/medialib.git
 */
public class FXMLDependencyInjection {
    public static Parent load(String location) throws IOException {
        FXMLLoader loader = getLoader(location);
        return loader.load();
    }

    public static FXMLLoader getLoader(String location) {
        return new FXMLLoader(
                FXMLDependencyInjection.class.getResource("/at/technikum_wien/tourplanner/view/" + location),
                null,
                //ResourceBundle.getBundle("at.technikum_wien.tourplanner.view." + "gui_strings", locale), //for languages
                new JavaFXBuilderFactory(),
                controllerClass-> ControllerFactory.getInstance().create(controllerClass)
        );
    }
}

