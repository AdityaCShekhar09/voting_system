module com.example.moviebookings {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires com.jfoenix;
    opens com.example.moviebookings to javafx.fxml;
    exports com.example.moviebookings;
}