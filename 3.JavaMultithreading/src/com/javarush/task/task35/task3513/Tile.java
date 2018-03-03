package com.javarush.task.task35.task3513;

import java.awt.*;

public class Tile {

    private final static int DEFAULT_FONT = 0x776e65;
    private final static int NEW_FONT = 0xf9f6f2;

    int value;

    public Tile() {
        value = 0;
    }

    public Tile(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public Color getFontColor() {
        return new Color(getFontColorValue());
    }

    public Color getTileColor() {
       return new Color(getTileColorValue());
    }

    private int getFontColorValue() {
        return value < 16 ? DEFAULT_FONT : NEW_FONT;
    }

    private int getTileColorValue() {
        switch (value) {
            case 0:
                return 0xcdc1b4;
            case 2:
                return 0xeee4da;
            case 4:
                return 0xede0c8;
            case 8:
                return 0xf2b179;
            case 16:
                return 0xf59563;
            case 32:
                return 0xf67c5f;
            case 64:
                return 0xf65e3b;
            case 128:
                return 0xedcf72;
            case 256:
                return 0xedcc61;
            case 512:
                return 0xedc850;
            case 1024:
                return 0xedc53f;
            case 2048:
                return 0xedc22e;
            default:
                return 0xff0000;
        }
    }
}
