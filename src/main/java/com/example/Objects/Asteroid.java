package com.example.Objects;

import com.example.Utils.ColorEnum;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;


public class Asteroid extends GameObject{
    
    private static double DefaultAsteroidVel = 10;

    private double asteriodVelocity;

    public Asteroid() {
        super(ColorEnum.Red,  new Circle());
        setCircleProps();
        this.asteriodVelocity = Math.random()* (25 - 7.5 + 1) + 7.5;
        //TODO Auto-generated constructor stub
    }

    private void setCircleProps(){
        this.shape.setTranslateX(Math.random()* (590 - 10 + 1) + 10);
        this.shape.setTranslateY(Math.random() * (50 - 10 + 1) + 10);
        ((Circle) this.shape).setRadius((int) (Math.random() * 15) + 15);
    }
    
    public double getAsteriodVelocity() {
        return asteriodVelocity;
    }

    public void setAsteriodVelocity(double asteriodVelocity) {
        this.asteriodVelocity = asteriodVelocity;
    }
   
    
}
