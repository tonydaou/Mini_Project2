module com.example.mini_project2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.example.mini_project2 to javafx.graphics;
    opens com.example.mini_project2.controllers to javafx.fxml;

    exports com.example.mini_project2;
    exports com.example.mini_project2.controllers;
}
