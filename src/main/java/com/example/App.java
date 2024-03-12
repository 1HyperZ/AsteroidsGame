package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.Objects.Spaceship;
import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    Spaceship spaceship;
    Pane gamePane;
    @Override
    public void start(Stage stage) throws IOException {
        gamePane = new Pane(); // Assuming you're using a Pane as the scene root
        gamePane.setPrefSize(800, 600);
        scene = new Scene(gamePane);
        spaceship = new Spaceship(new Position(10, 10), ColorEnum.Red);
        gamePane.getChildren().add(spaceship.getShape()); // Add spaceship shape to the Pane

        scene.setOnKeyPressed(event -> {
            System.out.println("bill");
            KeyCode key = event.getCode();
            if (key == KeyCode.W) {
                spaceship.moveUp(10);
            } else if (key == KeyCode.S) {
                spaceship.moveDown(10);
            }
            if (key == KeyCode.W) {
                spaceship.moveUp(10);
            } else if (key == KeyCode.S) {
                spaceship.moveDown(10);
            }
            updateGame(); // Call a method to update game state based on input
        });
        stage.setScene(scene);
        stage.setTitle("Asteroids Clone"); // Set the title of the game window
        stage.show(); // Display the game window
    }
    
    private void updateGame() {
        gamePane.getChildren().clear();
        gamePane.getChildren().add(spaceship.getShape());
    }

    public static void main(String[] args) {
        launch();
    }

}