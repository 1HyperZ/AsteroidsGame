package com.example.Objects;

import com.example.Utils.ColorEnum;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class SpaceshipBullet extends GameObject{
    //#region default bullet values
    public static double DefaultBulletVel = 25;
    private static double DefaultBulletRadius = 3;
    //#endregion

    //#region props
    private double bulletVelocity;
    private double spaceShipXTranslation;
    private double spaceShipYTranslation;
    //#endregion
    public SpaceshipBullet(double spaceShipX, double spaceShipY) {
        super(ColorEnum.White, new Circle(0,0, DefaultBulletRadius));
        this.shape.setTranslateX(spaceShipX);
        this.shape.setTranslateY(spaceShipY);
        this.bulletVelocity = DefaultBulletVel;
        //TODO Auto-generated constructor stub
    }


    public double getSpaceShipXTranslation() {
        return spaceShipXTranslation;
    }

    public double getSpaceShipYTranslation() {
        return spaceShipYTranslation;
    }

    public double getBulletVelocity() {
        return bulletVelocity;
    }
    
    

    
}
