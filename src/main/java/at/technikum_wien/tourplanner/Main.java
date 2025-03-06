package at.technikum_wien.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 768);
        stage.setTitle("R&R tour planner");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("javafx.runtime.version"));

        launch();
    }
}