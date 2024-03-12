module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports Controller to views.startPage.fxml;
    opens Controller to javafx.fxml;
}