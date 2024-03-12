package com.example.Objects;

import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

import javafx.scene.shape.Shape;

public class GameObjects {
    //#region props
    ColorEnum color;
    Position pos;
    Shape shape;
    //#endregion

    //#region C'tor
    public GameObjects(Position pos, ColorEnum color, Shape shape) {
        this.color = color;
        this.pos = pos;
        this.shape = shape;
    }
    //#endregion

    //#region methods
    public void moveUp(int amount){
        this.pos.moveUp(amount);    
    }
    public void moveDown(int amount){
        this.pos.moveUp(amount);    
    }
    public void moveRight(int amount){
        this.pos.moveUp(amount);    
    }
    public void moveLeft(int amount){
        this.pos.moveUp(amount);    
    }
    //#endregion

    //#region getters and setters
    public ColorEnum getColor() {
        return color;
    }
    public void setColor(ColorEnum color) {
        this.color = color;
    }
    public Position getPos() {
        return pos;
    }
    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Shape getShape() {
        return shape;
    }

    private void setShape(Shape shape) {
        this.shape = shape;
    }
    
    //#endregion
}
