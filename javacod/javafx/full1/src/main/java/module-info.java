module com.example.full1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.full1 to javafx.fxml;
    exports com.example.full1;
    opens com.example.full1.controller to javafx.fxml;
    exports com.example.full1.controller;
}