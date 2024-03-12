package com.example.Objects;

import com.example.Utils.ColorEnum;
import com.example.Utils.Position;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class GameObject {
    //#region props
    ColorEnum color;
    Shape shape;
    //#endregion

    //#region C'tor
    public GameObject(ColorEnum color, Shape shape) {
        this.color = color;
        this.shape = shape;
        this.shape.setFill(Color.rgb(color.red, color.green, color.blue));
    }

    public GameObject(Shape shape) {
        this.color = ColorEnum.Blue;
        this.shape = shape;
        this.shape.setFill(Color.rgb(color.red, color.green, color.blue));
    }
    //#endregion

    //#region methods
    public void moveY(double amount){
        this.shape.setTranslateY(shape.getTranslateY() + amount);    
    }
    public void moveX(double amount){
        this.shape.setTranslateX(shape.getTranslateX() + amount);    
    }
    //#endregion

    //#region getters and setters
    public ColorEnum getColor() {
        return color;
    }
    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }
    
    //#endregion
}
