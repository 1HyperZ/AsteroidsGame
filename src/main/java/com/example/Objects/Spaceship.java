package com.example.Objects;

import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.TriangleMesh;


public class Spaceship extends GameObject{

    public static final int DefaultSpawnX = 290;
    public static final int DefaultSpawnY = 560;

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
    
}