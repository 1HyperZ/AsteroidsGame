package com.example.Objects;

import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

import javafx.scene.shape.Polygon;


public class Asteroid extends GameObjects{

    public Asteroid(Position pos) {
        super(pos, ColorEnum.Black, null);
        int numSides = (int) (Math.random() * 5) + 5; // Generate random number of sides (e.g., 5-9) 
        this.shape = new Polygon(numSides);
        //TODO Auto-generated constructor stub
    }
   
}
