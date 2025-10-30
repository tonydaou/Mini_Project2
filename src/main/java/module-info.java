module com.example.mini_project2 {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.mini_project2;
    exports com.example.mini_project2.controllers;
    exports com.example.mini_project2.models;

    opens com.example.mini_project2.controllers to javafx.fxml;
    opens com.example.mini_project2.models to javafx.fxml;
}
