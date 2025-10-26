package com.example.mini_project2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text errorText;

    @FXML
    protected void handleLogin() throws IOException {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            errorText.setText("Please fill in all required fields.");
            return;
        }

        if (email.equals("Tony") && password.equals("Tony123")) {
            openHomePage("Tony");
        } else if (email.equals("Joe") && password.equals("Joe123")) {
            openHomePage("Joe");
        } else if (email.equals("Louis") && password.equals("Louis123")) {
            openHomePage("Louis");
        } else {
            errorText.setText("Incorrect username or password.");
        }
    }

    private void openHomePage(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mini_project2/home.fxml"));
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Home Page - Welcome " + username);
        stage.show();
    }
}