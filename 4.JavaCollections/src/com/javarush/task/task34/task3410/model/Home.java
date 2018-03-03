package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x, y, 2, 2);
    }

    @Override
    public void draw(Graphics graphics) {
        int startX = getX() - getWidth() / 2;
        int startY = getY() - getHeight() / 2;

        graphics.setColor(Color.red);
        graphics.drawOval(startX, startY, getWidth(), getHeight());
    }
}
