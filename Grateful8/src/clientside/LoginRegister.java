package clientside;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginRegister extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login/Register");

        // Create the grid pane and set its properties
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create the labels and text fields for the user name and password
        Label userNameLabel = new Label("UserName:");
        TextField userNameField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();

        // Add the labels and text fields to the grid pane
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);

        // Create the login and register buttons
        Button loginButton = new Button("Log-in");
        Button registerButton = new Button("Register");

        // Add the buttons to the grid pane
        gridPane.add(loginButton, 0, 2);
        gridPane.add(registerButton, 1, 2);

        // Set the action for the login button
        loginButton.setOnAction(e -> {
            // TODO: Add login logic here
            System.out.println("Log-in button clicked");
        });

        // Set the action for the register button
        registerButton.setOnAction(e -> {
            // TODO: Add register logic here
            System.out.println("Register button clicked");
        });

        // Create the scene and add the grid pane to it
        Scene scene = new Scene(gridPane, 300, 150);

        // Set the scene for the primary stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}