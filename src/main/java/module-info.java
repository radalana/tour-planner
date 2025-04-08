module at.technikum_wien.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires static lombok;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens at.technikum_wien.tourplanner to javafx.fxml;
    opens at.technikum_wien.tourplanner.model to javafx.base, com.fasterxml.jackson.databind;//for reflection in tourTable
    opens at.technikum_wien.tourplanner.view to javafx.fxml;

    exports at.technikum_wien.tourplanner;
    exports at.technikum_wien.tourplanner.view;
    exports at.technikum_wien.tourplanner.viewmodel;
    exports at.technikum_wien.tourplanner.model;
    exports at.technikum_wien.tourplanner.httpClient;
}