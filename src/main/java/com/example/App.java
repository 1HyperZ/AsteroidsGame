package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.Objects.Asteroid;
import com.example.Objects.Spaceship;
import com.example.Objects.SpaceshipBullet;
import com.example.Utils.ColorEnum;
import com.example.Utils.LeaderboardEntry;
import com.example.Utils.LeaderboardEntryComparator;

import static com.example.Constants.*;
/**
 * JavaFX App
 */
public class App extends Application {

    AnimationTimer gameLoop;
    Group root;
    Scene scene;
    Spaceship spaceship;
    List<Asteroid> asteroids;
    List<SpaceshipBullet> bullets;

    Text newBestScoreText;
    Text top5Text;
    ListView<LeaderboardEntry> top5Leaderboard;
    Text enterYourNameText;
    TextField userNameTextField;
    Label pressEnterLabel;
    Text scoreLabel;
    Label endGameLabel;
    Label bestScoreLabel;
    MediaPlayer shootSound;
    String shootSoundPath;
    
    String userName;

    double lastUpdateToAsteroidSpeedTimeStamp;
    double lastAsteroidSpawnTime;
    double lastAsteroidMoveTime;
    double lastBulletsMoveTime;
    int bestScore;
    int score;
    boolean gameLoopRunning;
    boolean isGameStarted;
    
    @Override
    public void start(Stage stage) throws IOException {
       System.out.println("Starting Game");

        //#region initializing obejcts
        root = new Group(); 
        scene = new Scene(root, SceneSizeX, SceneSizeY, Color.BLACK);
        spaceship = new Spaceship(ColorEnum.Blue);

        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();
        lastUpdateToAsteroidSpeedTimeStamp = 0;
        lastAsteroidMoveTime = 0;
        lastAsteroidSpawnTime = 0;
        lastBulletsMoveTime = 0;
        score = 0;
        gameLoopRunning = false;
        isGameStarted = false;
        //#endregion

        //#region sounds
        shootSoundPath = getClass().getResource("sounds/shoot_sound.wav").toString();
        shootSound = new MediaPlayer(new Media(shootSoundPath));
        shootSound.setVolume(ShootSoundVolume);

        //#endregion

        //#region labels and text

        //#region score labels and text
        scoreLabel = new Text(ScoreLabelTranslationX, ScoreLabelTranslationY, "Score: " + score);
        scoreLabel.setFont(new Font(ScoreLabelFontSize));
        scoreLabel.setFill(Color.WHITE);

        bestScoreLabel = new Label("Best Score: " + bestScore);
        bestScoreLabel.setVisible(false);
        bestScoreLabel.setTranslateX(280);
        bestScoreLabel.setTranslateY(370);
        bestScoreLabel.setScaleX(BestScoreLabelScale);
        bestScoreLabel.setScaleY(BestScoreLabelScale);
        bestScoreLabel.setTextFill(Color.WHITE);
        //#endregion

        //#region endgame labels and leaderboard
        pressEnterLabel = new Label("Press Enter To Play Again");
        pressEnterLabel.setVisible(false);
        pressEnterLabel.setTranslateX(PressEnterLabelTranslationX);
        pressEnterLabel.setTranslateY(PressEnterLabelTranslationY);
        pressEnterLabel.setScaleX(PressEnterLabelScale);
        pressEnterLabel.setScaleY(PressEnterLabelScale);
        pressEnterLabel.setTextFill(Color.WHITE);

        endGameLabel = new Label("Game Over");
        endGameLabel.setVisible(false);
        endGameLabel.setTranslateX(EndGameLabelTranslationX);
        endGameLabel.setTranslateY(EndGameLabelTranslationY);
        endGameLabel.setScaleX(EndGameLabelScale);
        endGameLabel.setScaleY(EndGameLabelScale);
        endGameLabel.setTextFill(Color.WHITE);

        top5Leaderboard = new ListView<>();
        top5Leaderboard.setVisible(false);
        top5Leaderboard.setTranslateX(Top5LeaderboardTranslationX);
        top5Leaderboard.setTranslateY(Top5LeaderboardTranslationY);
        top5Leaderboard.setScaleX(Top5LeaderboardScale);
        top5Leaderboard.setScaleY(Top5LeaderboardScale);
        top5Leaderboard.setMaxSize(Top5LeaderboardMaxWidth, Top5LeaderboardMaxHeight);
        top5Leaderboard.getStyleClass().add("my-listview");
        scene.getStylesheets().add(getClass().getResource("Utils/leaderboard_style.css").toExternalForm());

        top5Text = new Text(Top5TextTranslationX, Top5TextTranslationY, "Top 5:");
        top5Text.setFont(new Font(Top5TextSFontSize));
        top5Text.setVisible(false);
        top5Text.setFill(Color.WHITE);

        newBestScoreText = new Text(NewBestScoreTextFieldTranslationX, NewBestScoreTextFieldTranslationY, "New Best Score!");
        newBestScoreText.setFont(new Font(NewBestScoreTextFieldFontSize));
        newBestScoreText.setVisible(false);
        newBestScoreText.setFill(Color.WHITE);

        //#endregion
        

        //#region username text and textbox
        enterYourNameText = new Text(EnterYourNameTextTranslationX, EnterYourNameTextTranslationY, "Enter your name:");
        enterYourNameText.setFont(new Font(EnterYourNameTextFontSize));
        enterYourNameText.setFill(Color.WHITE);
        enterYourNameText.setVisible(true);

        userNameTextField = new TextField();
        userNameTextField.setTranslateX(UserNameTextFieldTranslationX);
        userNameTextField.setTranslateY(UserNameTextFieldTranslationY);
        userNameTextField.setVisible(true);
        userNameTextField.setScaleX(UserNameTextFieldScale);
        userNameTextField.setScaleY(UserNameTextFieldScale);
        //#endregion

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
                if(timestamp - lastAsteroidMoveTime >= TimeBetweenAsteroidsMove){
                    lastAsteroidMoveTime = timestamp;
                    for (Asteroid asteroid : asteroids) {
                        asteroid.moveY(asteroid.getAsteriodVelocity());
                    }
                }
                if(timestamp - lastUpdateToAsteroidSpeedTimeStamp >= TimeBetweenAsteroidsSpawnAndSpeedUpdate){
                    Asteroid.updateAsteroidSpawnTime();
                    Asteroid.updateAsteroidSpeed();
                    lastUpdateToAsteroidSpeedTimeStamp = timestamp;
                    System.out.println("Update speed and spawn time");
                }
                //#endregion
                if(timestamp - lastBulletsMoveTime >= TimeBetweenBulletsMove){
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
                            scoreLabel.toFront();
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
                        shootSound.stop();
                        shootSound.play();
                    }
                }
                
            }

        });
        //#endregion

        //#region key press
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if(isGameStarted == false){
                if(key == KeyCode.ENTER)
                    startGame();
            }
            else{
                if(gameLoopRunning){
                    if (key == KeyCode.W|| key == KeyCode.UP) {
                        // shootSound.stop();
                        // shootSound.play();
                        if(spaceship.getShape().getTranslateY() > SpaceshipMaxTranslationY)
                            spaceship.moveY(-SpaceshipMovementLength);
                    } 
                    else if (key == KeyCode.S || key == KeyCode.DOWN) {
                        // shootSound.stop();
                        // shootSound.play();
                        if(spaceship.getShape().getTranslateY() < 590)
                            spaceship.moveY(SpaceshipMovementLength);
                    }
                    else if (key == KeyCode.D|| key == KeyCode.RIGHT) {
                        // shootSound.stop();
                        // shootSound.play();
                        if(spaceship.getShape().getTranslateX() < 580)
                            spaceship.moveX(SpaceshipMovementLength);
                    } 
                    else if (key == KeyCode.A|| key == KeyCode.LEFT) {
                        // shootSound.stop();
                        // shootSound.play();
                        if(spaceship.getShape().getTranslateX() > 0)
                            spaceship.moveX(-SpaceshipMovementLength);
                    }
                }
                else{
                    if(key == KeyCode.ENTER){
                        resetGame();
                    }

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

    

    private void startGame() {
        userName = userNameTextField.getText();
        if(userName.length() < UsernameMinimumLength){
            System.out.println("Username is not valid! try again");
            return;
        }
        if(Database.isNewUser(userName)){
            System.out.println("Welcome " + userName);
        }else{
            System.out.println("Welcome back " + userName);
        }
        gameLoop.start();
        userNameTextField.setVisible(false);
        enterYourNameText.setVisible(false);
        isGameStarted = true;
        gameLoopRunning = true;
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
        top5Leaderboard.setVisible(false);
        top5Text.setVisible(false);
        newBestScoreText.setVisible(false);
        gameLoop.start();
        score = 0;
        scoreLabel.setText("Score: " + score);
        gameLoopRunning = true;
        addObjectsToScreen();
    }
    
    private void gameOver() {
        if(score > bestScore){
            bestScore = score;
            newBestScoreText.setVisible(true);
            newBestScoreText.toFront();
        }
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
        showTop5Leaderboard();
    }

    private void newGameOver() {
        if(score > Database.getUserBestScore(userName)){
            Database.setUserBestScore(userName, score);
            newBestScoreText.setVisible(true);
            newBestScoreText.toFront();
        }
        bestScore = Database.getUserBestScore(userName);
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
        showTop5Leaderboard();
    }
    //#endregion
    
    private void updateTop5Leaderboard(){
        top5Leaderboard.getItems().clear();

        List<LeaderboardEntry> leaderboardList = new ArrayList<>();  //temo
        // List<LeaderboardEntry> leaderboardList = Database.getUserScoreList();
        leaderboardList.add(new LeaderboardEntry("bill", 5));
        leaderboardList.add(new LeaderboardEntry("gilad", 7));
        leaderboardList.sort(new LeaderboardEntryComparator());
        for (int i = 0; i < 5 && i < leaderboardList.size(); i++) {
            top5Leaderboard.getItems().add(leaderboardList.get(i));
        }
    }

    private void showTop5Leaderboard() {
        updateTop5Leaderboard();
        top5Leaderboard.setVisible(true);
        top5Leaderboard.toFront();
        top5Text.setVisible(true);
        top5Text.toFront();
    }



    //#region util methods
    private void addObjectsToScreen(){
        root.getChildren().add(newBestScoreText);
        root.getChildren().add(top5Text);
        root.getChildren().add(top5Leaderboard);
        root.getChildren().add(enterYourNameText);
        root.getChildren().add(userNameTextField);
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