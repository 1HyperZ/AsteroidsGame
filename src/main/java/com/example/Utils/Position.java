package com.example.Utils;

public class Position {
    //#region props
    private int x;
    private int y;
    //#endregion

    //#region C'tor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //#endregion

    //#region methods
    public void moveUp(int amount){
        this.y += amount;
    }
    public void moveDown(int amount){
        this.y -= amount;
    }
    public void moveLeft(int amount){
        this.x -= amount;
    }
    public void moveRight(int amount){
        this.x += amount;
    }
    
    //#endregion
    
    //#region getters and setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    //#endregion
    
}
