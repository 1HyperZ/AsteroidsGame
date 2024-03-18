package com.example.Objects;

import com.example.Utils.ColorEnum;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.TriangleMesh;


public class Spaceship extends GameObject{
    
    //#region default spaceship spawn coordinates
    public static final int DefaultSpawnX = 290;
    public static final int DefaultSpawnY = 560;
    //#endregion
    
    //#region C'tors
    public Spaceship(ColorEnum color) {
        super(color, new Rectangle(20, 10));
        this.shape.setTranslateX(DefaultSpawnX);
        this.shape.setTranslateY(DefaultSpawnY);
        //TODO Auto-generated constructor stub
    }

    public Spaceship(ColorEnum color, double x, double y) {
        super(color, new Rectangle(20, 10));
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);
        //TODO Auto-generated constructor stub
    }
    //#endregion

}
