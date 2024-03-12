package com.example.Utils;

public enum ColorEnum {

    
    Blue(0,0,255)
    , Red(255, 0, 0)
    , Green(0, 255, 0)
    , Black(0,0,0);

    public int red;
    public int green;
    public int blue;

    ColorEnum(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }
}
