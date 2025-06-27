package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.view.ControllerFactory;
import at.technikum_wien.tourplanner.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        ServiceProvider serviceProvider = new ServiceProvider();
        ControllerFactory controllerFactory = new ControllerFactory(new ViewModelFactory(serviceProvider));
        FXMLDependencyInjection.setControllerFactory(controllerFactory);

        Parent root = FXMLDependencyInjection.load("homepage.fxml");
        Scene scene = new Scene(root);
        primaryStage.setTitle("R&R tour planner");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}