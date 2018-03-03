package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;


public class Model {
    // Этот размер будет участвовать в расчёте движения и столкновений объектов
    public final static int FIELD_CELL_SIZE = 20;

    private EventListener eventListener;
    private GameObjects gameObjects;

    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("C:\\Users\\SergeyMajor\\IdeaProjects\\JavaRushTasks\\JavaRushTasks\\4.JavaCollections\\src\\com\\javarush\\task\\task34\\task3410\\res\\levels.txt"));

    public void setEventListener(EventListener eventListeners) {
        this.eventListener = eventListeners;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        this.gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restartLevel(currentLevel);
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();

        if (checkWallCollision(gameObjects.getPlayer(), direction)) return;
        if (checkBoxCollisionAndMoveIfAvaliable(direction)) return;

        move(direction, player);
        checkCompletion();
    }

    private void move(Direction direction, Movable gameObject) {
        switch (direction) {
            case LEFT:
                gameObject.move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                gameObject.move(FIELD_CELL_SIZE, 0);
                break;
            case UP:
                gameObject.move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                gameObject.move(0, FIELD_CELL_SIZE);
                break;
        }
    }


    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) return true;
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction) {
        Player player = gameObjects.getPlayer();
        GameObject stoped = null;
        for (GameObject gameObject : gameObjects.getAll()) {
            if (!(gameObject instanceof Player) && !(gameObject instanceof Home) && player.isCollision(gameObject, direction)) {
                stoped = gameObject;
            }
        }

        if ((stoped == null)) {
            return false;
        }

        if (stoped instanceof Box) {
            Box stopedBox = (Box) stoped;
            if (checkWallCollision(stopedBox, direction)) {
                return true;
            }
            for (Box box : gameObjects.getBoxes()) {
                if (stopedBox.isCollision(box, direction)) {
                    return true;
                }
            }

            switch (direction) {
                case LEFT:
                    stopedBox.move(-FIELD_CELL_SIZE, 0);
                    break;
                case RIGHT:
                    stopedBox.move(FIELD_CELL_SIZE, 0);
                    break;
                case UP:
                    stopedBox.move(0, -FIELD_CELL_SIZE);
                    break;
                case DOWN:
                    stopedBox.move(0, FIELD_CELL_SIZE);
            }
        }
        return false;

    }

    public void checkCompletion() {
        boolean isLevelCompleted = true;
        for (Home home : gameObjects.getHomes()) {
            boolean atHome = false;
            for (Box box : gameObjects.getBoxes()) {
                if ((home.getX() == box.getX()) && (home.getY() == box.getY())) {
                    atHome = true;
                }
            }
            if (!atHome) isLevelCompleted = false;
        }
        if (isLevelCompleted) eventListener.levelCompleted(currentLevel);
    }
}
