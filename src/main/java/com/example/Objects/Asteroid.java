package com.example.Objects;

import com.example.Utils.ColorEnum;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;


public class Asteroid extends GameObject{
    
    //#region default values
    private static double DefaultAsteroidMinVel = 7.5;
    private static double DefaultAsteroidMaxVel = 25;   
    private static double DefaultAsteroidsSpawnTime = 2;
    //#endregion
    
    //#region asteroid velocity and spawn time values
    private static double AsteroidMinVel = 7.5;
    private static double AsteroidMaxVel = 25;
    private static double AsteroidVelMultiplier = 1.25;
   
    private static double AsteroidSpawnTimeMultiplier = 0.8;
    private static double AsteroidsSpawnTime = 2;
    //#endregion

    private double asteriodVelocity;

    //#region C'tor
    public Asteroid() {
        super(ColorEnum.Red,  new Circle());
        setCircleProps();
        this.asteriodVelocity = Math.random()* (AsteroidMaxVel - AsteroidMinVel + 1)
        
        + AsteroidMinVel;
        //TODO Auto-generated constructor stub
    }
    //#endregion
    
    //#region util methods
    private void setCircleProps(){
        this.shape.setTranslateX(Math.random()* (590 - 10 + 1) + 10);
        this.shape.setTranslateY(Math.random() * (50 - 10 + 1) + 10);
        ((Circle) this.shape).setRadius((int) (Math.random() * 15) + 15);
    }

    public static void updateAsteroidSpeed(){
        AsteroidMaxVel *= AsteroidVelMultiplier;
        AsteroidMinVel *= AsteroidVelMultiplier;
    }
    public static void updateAsteroidSpawnTime() {
        AsteroidsSpawnTime *= AsteroidSpawnTimeMultiplier;
    }
    public static void resetAsteroidsValuesToDefault() {
        AsteroidMaxVel = DefaultAsteroidMaxVel;
        AsteroidMinVel = DefaultAsteroidMinVel;
        AsteroidsSpawnTime =DefaultAsteroidsSpawnTime;
    }
    //#endregion

    //#region getters
    public double getAsteriodVelocity() {
        return asteriodVelocity;
    }
    public static double getAsteroidsSpawnTime() {
        return AsteroidsSpawnTime;
    }
    public static double getAsteroidVelMultiplier() {
        return AsteroidVelMultiplier;
    }

    public static double getAsteroidSpawnTimeMultiplier() {
        return AsteroidSpawnTimeMultiplier;
    }
    //#endregion
    
}
