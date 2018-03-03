package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        int startX = getX() - getWidth() / 2;
        int startY = getY() - getHeight() / 2;

        graphics.setColor(Color.yellow);
        graphics.fillOval(startX, startY, getWidth(), getHeight());
    }

    @Override
    public void move(int x, int y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }
}
