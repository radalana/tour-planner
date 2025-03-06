module at.technikum_wien.tourplanner {
    //requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;

    opens at.technikum_wien.tourplanner to javafx.fxml;
    exports at.technikum_wien.tourplanner;
}