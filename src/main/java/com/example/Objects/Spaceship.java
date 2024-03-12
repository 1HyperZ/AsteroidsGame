package com.example.Objects;

import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.TriangleMesh;


public class Spaceship extends GameObjects{

    public Spaceship(Position pos, ColorEnum color) {
        super(pos, color, new Rectangle(20, 10, Color.rgb(color.red, color.green, color.blue)));
        //TODO Auto-generated constructor stub
    }
    
}
