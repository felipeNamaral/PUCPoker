module com.poker.poker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;

    opens com.poker.poker to javafx.fxml;
    exports com.poker.poker;
}