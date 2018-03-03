package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        int startX = getX() - getWidth() / 2;
        int startY = getY() - getHeight() / 2;

        graphics.setColor(Color.darkGray);
        graphics.fillRect(startX, startY, getWidth(), getHeight());
    }
}
