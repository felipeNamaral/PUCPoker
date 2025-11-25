module com.poker.poker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires google.genai;
    requires javafx.base;
    requires com.google.gson;
    requires java.dotenv;

    opens com.poker.poker to javafx.fxml;
    exports com.poker.poker;
}