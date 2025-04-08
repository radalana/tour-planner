package at.technikum_wien.tourplanner;

import at.technikum_wien.tourplanner.httpClient.TourService;
import at.technikum_wien.tourplanner.view.ControllerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpClient;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        TourService tourService = new TourService(client);

        ControllerFactory controllerFactory = new ControllerFactory(tourService);
        FXMLDependencyInjection.setControllerFactory(controllerFactory);

        Parent root = FXMLDependencyInjection.load("homepage.fxml");
        Scene scene = new Scene(root);
        primaryStage.setTitle("R&R tour planner");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}