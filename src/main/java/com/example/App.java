package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.Objects.Asteroid;
import com.example.Objects.Spaceship;
import com.example.Objects.SpaceshipBullet;
import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

/**
 * JavaFX App
 */
public class App extends Application {
    public static int sceneSizeX = 600;
    public static int sceneSizeY = 600;


    AnimationTimer gameLoop;
    Group root;
    Scene scene;
    Spaceship spaceship;
    List<Asteroid> asteroids;
    List<SpaceshipBullet> bullets;

    Label pressEnterLabel;
    Text scoreLabel;
    Label endGameLabel;
    Label bestScoreLabel;
    MediaPlayer moveSound;
    String moveSoundPath;
    
    double lastUpdateToAsteroidSpeedTimeStamp;
    double lastAsteroidSpawnTime;
    double lastAsteroidMoveTime;
    double lastBulletsMoveTime;
    int bestScore;
    int score;
    boolean gameLoopRunning;
    
    @Override
    public void start(Stage stage) throws IOException {
       System.out.println("Starting Game");

        //#region initializing obejcts
        root = new Group(); 
        scene = new Scene(root, sceneSizeX, sceneSizeY, Color.BLACK);
        spaceship = new Spaceship(ColorEnum.Blue);

        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();
        lastUpdateToAsteroidSpeedTimeStamp = 0;
        lastAsteroidMoveTime = 0;
        lastAsteroidSpawnTime = 0;
        lastBulletsMoveTime = 0;
        score = 0;
        gameLoopRunning = false;
        //#endregion

        //#region sounds
        moveSoundPath = "file:/C:/Users/omerz/Documents/VS%20Code%20Projects/Java/AsteroidsGame/src/main/resources/com/example/sounds/move_sound.wav";
        moveSound = new MediaPlayer(new Media(moveSoundPath));
        moveSound.setVolume(0.05);

        //#endregion

        //#region labels
        scoreLabel = new Text(500, 50, "Score: " + score);
        scoreLabel.setFont(new Font(20));
        scoreLabel.setFill(Color.WHITE);

        pressEnterLabel = new Label("Press Enter To Play Again");
        pressEnterLabel.setVisible(false);
        pressEnterLabel.setTranslateX(250);
        pressEnterLabel.setTranslateY(200);
        pressEnterLabel.setScaleX(3);
        pressEnterLabel.setScaleY(3);
        pressEnterLabel.setTextFill(Color.WHITE);

        endGameLabel = new Label("Game Over");
        endGameLabel.setVisible(false);
        endGameLabel.setTranslateX(280);
        endGameLabel.setTranslateY(300);
        endGameLabel.setScaleX(5);
        endGameLabel.setScaleY(5);
        endGameLabel.setTextFill(Color.WHITE);


        bestScoreLabel = new Label("Best Score: " + bestScore);
        bestScoreLabel.setVisible(false);
        bestScoreLabel.setTranslateX(280);
        bestScoreLabel.setTranslateY(370);
        bestScoreLabel.setScaleX(4);
        bestScoreLabel.setScaleY(4);
        bestScoreLabel.setTextFill(Color.WHITE);

        //#endregion

        //#region game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double timestamp = (double) now / 1000000000;
                //#region spawinging asteroids and making them move
                if(timestamp - lastAsteroidSpawnTime >= Asteroid.getAsteroidsSpawnTime()){
                    spawnAsteroid();
                    lastAsteroidSpawnTime = timestamp;
                    
                }
                if(timestamp - lastAsteroidMoveTime >= 0.5){
                    lastAsteroidMoveTime = timestamp;
                    for (Asteroid asteroid : asteroids) {
                        asteroid.moveY(asteroid.getAsteriodVelocity());
                    }
                }
                if(timestamp - lastUpdateToAsteroidSpeedTimeStamp >= 10){
                    Asteroid.updateAsteroidSpawnTime();
                    Asteroid.updateAsteroidSpeed();
                    lastUpdateToAsteroidSpeedTimeStamp = timestamp;
                    System.out.println("Update speed and spawn time");
                }
                //#endregion
                if(timestamp - lastBulletsMoveTime >= 0.1){
                    lastBulletsMoveTime = timestamp;
                    for (SpaceshipBullet bullet : bullets) {
                        bullet.moveY(-bullet.getBulletVelocity());
                    }
                }
                
                for (int i = 0 ; i < asteroids.size() ; i++) {
                    for(SpaceshipBullet bullet : bullets){
                        if (asteroids.get(i).getShape().getBoundsInParent().intersects(bullet.getShape().getBoundsInParent())) {
                            root.getChildren().remove(asteroids.get(i).getShape());
                            root.getChildren().remove(bullet.getShape());
                            bullets.remove(bullet);
                            asteroids.remove(i);
                            score++;
                            scoreLabel.setText("Score: " + score);
                            break;
                        }
                    }
                }

                for (Asteroid asteroid : asteroids) {
                    if (spaceship.getShape().getBoundsInParent().intersects(asteroid.getShape().getBoundsInParent()) 
                        || asteroid.getShape().getTranslateY() > 590) {
                        gameOver(); // Call game over method
                    }
                }
            }

        };
        gameLoop.start();
        gameLoopRunning = true;
        //#endregion

        //#region Mouse clicks
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                // This method will be called on mouse click
                if(gameLoopRunning){
                    if (event.getButton() == MouseButton.PRIMARY) {
                        // Left click detected!
                        shotSpaceshipBullet();
                        moveSound.stop();
                        moveSound.play();
                    }
                }
                
            }

        });
        //#endregion

        //#region key press
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if(gameLoopRunning){
                if (key == KeyCode.W|| key == KeyCode.UP) {
                    // moveSound.stop();
                    // moveSound.play();
                    if(spaceship.getShape().getTranslateY() > 300)
                        spaceship.moveY(-10);
                } 
                else if (key == KeyCode.S || key == KeyCode.DOWN) {
                    // moveSound.stop();
                    // moveSound.play();
                    if(spaceship.getShape().getTranslateY() < 590)
                        spaceship.moveY(10);
                }
                else if (key == KeyCode.D|| key == KeyCode.RIGHT) {
                    // moveSound.stop();
                    // moveSound.play();
                    if(spaceship.getShape().getTranslateX() < 580)
                        spaceship.moveX(10);
                } 
                else if (key == KeyCode.A|| key == KeyCode.LEFT) {
                    // moveSound.stop();
                    // moveSound.play();
                    if(spaceship.getShape().getTranslateX() > 0)
                        spaceship.moveX(-10);
                }
            }
            else{
                if(key == KeyCode.ENTER){
                    resetGame();
                }

            }
        });
        //#endregion

        addObjectsToScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Asteroids Clone"); // Set the title of the game window
        stage.show(); // Display the game window
    }

    

    //#region game methods
    private void resetGame() {
        // TODO Auto-generated method stub
        System.out.println("Restarting Game");
        asteroids.clear();
        bullets.clear();
        root.getChildren().clear();
        spaceship.getShape().setTranslateX(Spaceship.DefaultSpawnX);
        spaceship.getShape().setTranslateY(Spaceship.DefaultSpawnY);
        endGameLabel.setVisible(false);
        bestScoreLabel.setVisible(false);
        pressEnterLabel.setVisible(false);
        gameLoop.start();
        score = 0;
        scoreLabel.setText("Score: " + score);
        gameLoopRunning = true;
        addObjectsToScreen();
    }
    
    private void gameOver() {
        if(score > bestScore)
            bestScore = score;
        gameLoopRunning = false;
        System.out.println("Game Over");
        gameLoop.stop();
        endGameLabel.setVisible(true);
        endGameLabel.toFront();
        bestScoreLabel.setText("Best Score: " + bestScore);
        bestScoreLabel.setVisible(true);
        bestScoreLabel.toFront();
        pressEnterLabel.setVisible(true);
        pressEnterLabel.toFront();
        Asteroid.resetAsteroidsValuesToDefault();
    }
    //#endregion


    //#region util methods
    private void addObjectsToScreen(){
        root.getChildren().add(spaceship.getShape());
        root.getChildren().add(endGameLabel);
        root.getChildren().add(bestScoreLabel);
        root.getChildren().add(scoreLabel);
        root.getChildren().add(pressEnterLabel);        
    }

    private void shotSpaceshipBullet(){
        SpaceshipBullet spaceshipBullet = new SpaceshipBullet(spaceship.getShape().getTranslateX() + 10, 
        spaceship.getShape().getTranslateY());
        root.getChildren().add(spaceshipBullet.getShape());
        bullets.add(spaceshipBullet);
    } 

    private void spawnAsteroid(){
        Asteroid asteroid = new Asteroid();
        root.getChildren().add(asteroid.getShape());
        asteroids.add(asteroid);
    }
    //#endregion
    
    public static void main(String[] args) {
        launch(args);
    }

}