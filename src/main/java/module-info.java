module com.poker.poker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires google.genai;
    requires javafx.base;

    opens com.poker.poker to javafx.fxml;
    exports com.poker.poker;
}