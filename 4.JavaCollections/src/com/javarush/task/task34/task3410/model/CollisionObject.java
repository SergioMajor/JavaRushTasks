package com.javarush.task.task34.task3410.model;

public abstract class CollisionObject extends GameObject {
    public CollisionObject(int x, int y) {
        super(x, y);
    }

    /**
     * Метод возвращает true, если при перемещении текущегообъекта в направлении direction
     * на FIELD_CELL_SIZE произойдет столкновение с объектом gameObject, переданным в качестве параметра
     */
    public boolean isCollision(GameObject gameObject, Direction direction) {
        int dx = getX();
        int dy = getY();

        switch (direction) {
            case LEFT:
                dx = getX() - Model.FIELD_CELL_SIZE;
                break;
            case RIGHT:
                dx = getX() + Model.FIELD_CELL_SIZE;
                break;
            case UP:
                dy = getY() - Model.FIELD_CELL_SIZE;
                break;
            case DOWN:
                dy = getY() + Model.FIELD_CELL_SIZE;
                break;
        }

        // Столкновением считать совпадение координат x и y
        return gameObject.getX() == dx && gameObject.getY() == dy;
    }
}
