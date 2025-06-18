module at.technikum_wien.tourplanner {
    requires javafx.fxml;
    requires javafx.web;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires org.mapstruct;
    requires org.json;
    requires org.controlsfx.controls;
    requires java.net.http;

    opens at.technikum_wien.tourplanner to javafx.fxml;
    opens at.technikum_wien.tourplanner.model to javafx.base, com.fasterxml.jackson.databind;//for reflection in tourTable
    opens at.technikum_wien.tourplanner.view to javafx.fxml;

    exports at.technikum_wien.tourplanner;
    exports at.technikum_wien.tourplanner.view;
    exports at.technikum_wien.tourplanner.viewmodel;
    exports at.technikum_wien.tourplanner.model;
    exports at.technikum_wien.tourplanner.httpClient;
    exports at.technikum_wien.tourplanner.dto to com.fasterxml.jackson.databind;
}