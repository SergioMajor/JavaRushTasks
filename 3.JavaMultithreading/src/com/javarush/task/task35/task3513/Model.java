package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private final static int FIELD_WIDTH = 4;

    private Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

    int score = 0;
    int maxTile = 2;

    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    /**
     * Инициализация поля
     */
    protected void resetGameTiles() {

        // Заполнем поле пустими значениями
        for (int n = 0; n < FIELD_WIDTH; n++) {
            for (int m = 0; m < FIELD_WIDTH; m++) {
                gameTiles[n][m] = new Tile();
            }
        }
        
        // Заполенеие поля 2 плитками
        addTile();
        addTile();
    }

    /**
     *   Смотрит какие плитки пустуют
     *   Менять вес одной из них
     */
    public void addTile() {

        // Вибор пустой плитки
        List<Tile> emptyTiles = getEmptyTiles();

        if (!emptyTiles.isEmpty()) {
            Tile randomTile = emptyTiles.get((int) (emptyTiles.size() * Math.random()));

            for (Tile[] tiles : gameTiles) {
                for (Tile tile : tiles) {
                    if (tile == randomTile) {

                        // На 9 двоек должна приходиться 1 четверка
                        tile.value = Math.random() < 0.9 ? 2 : 4;
                    }
                }
            }
        }
    }

    /**
     * Список пустих плиток
     * @return List<Tile>
     */
    private List<Tile> getEmptyTiles() {
        List<Tile> emptyList = new ArrayList<>();

        for (Tile[] tiles : gameTiles)
            for (Tile tile : tiles)
                if (tile.isEmpty())
                    emptyList.add(tile);

        return emptyList;
    }



    public void left() {
        if (isSaveNeeded) saveState(gameTiles);

        boolean change = false;

        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i]))
                change = true;
        }

        if (change)
            addTile();

        isSaveNeeded = true;
    }

    public void right() {
        saveState(gameTiles);
        rotate();
        rotate();
        left();
        rotate();
        rotate();
    }

    public void up() {
        saveState(gameTiles);
        rotate();
        left();
        rotate();
        rotate();
        rotate();
    }

    public void down() {
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        left();
        rotate();
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean change = false;

        for (int i = 1; i < tiles.length; i++) {
            int j = i;

            while (j > 0 && tiles[j - 1].value == 0) {
                // Меняем плитки местами
                if (tiles[j].value != 0) {
                    tiles[j - 1].value = tiles[j].value;
                    tiles[j].value = 0;

                    if (!change)
                        change = true;
                }

                j--;
            }
        }

        return change;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isChanged = false;
        for (int j = 0; j < 3; j++) {
            if (tiles[j].getValue() != 0 && tiles[j].getValue() == tiles[j + 1].getValue()) {
                tiles[j].setValue(tiles[j].getValue() * 2);
                tiles[j + 1].setValue(0);
                if (tiles[j].getValue() > maxTile) maxTile = tiles[j].getValue();
                score += tiles[j].getValue();
                isChanged = true;
            }
        }

        if (isChanged) {
            Tile temp;
            for (int j = 0; j < 3; j++) {
                if (tiles[j].getValue() == 0 && tiles[j + 1].getValue() != 0) {
                    temp = tiles[j];
                    tiles[j] = tiles[j + 1];
                    tiles[j + 1] = temp;
                }
            }
        }

        return isChanged;
    }

    private void rotate() {

        // border -> center
        for (int n = 0; n < FIELD_WIDTH / 2; n++) {
            // left -> right
            for (int m = n; m < FIELD_WIDTH - 1 - n; m++) {
                Tile tmp = gameTiles[n][m];
                gameTiles[n][m] = gameTiles[m][FIELD_WIDTH - 1 - n];
                gameTiles[m][FIELD_WIDTH - 1 - n] = gameTiles[FIELD_WIDTH - 1 - n][FIELD_WIDTH - 1 - m];
                gameTiles[FIELD_WIDTH - 1 - n][FIELD_WIDTH - 1 - m] = gameTiles[FIELD_WIDTH - 1 - m][n];
                gameTiles[FIELD_WIDTH - 1 - m][n] = tmp;
            }
        }
    }

    public boolean canMove() {
        if (!getEmptyTiles().isEmpty()) {
            return true;
        }

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int n = 0; n < FIELD_WIDTH; n++) {
                for (int m = 1; m < FIELD_WIDTH; m++) {
                    if (gameTiles[i][m - 1].value == gameTiles[i][m].value)
                        return true;
                }
            }
            rotate();
        }

        return false;
    }

    private void saveState(Tile[][] gameTiles) {

        Tile[][] newTile = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < gameTiles.length; i++){
            for(int j = 0; j < gameTiles[i].length; j++){
                newTile[i][j] = new Tile(gameTiles[i][j].value);
            }
        }

        previousStates.push(newTile);
        previousScores.push(score);

        isSaveNeeded = false;
    }

    public void rollback() {
        if(!previousStates.isEmpty())
            gameTiles = previousStates.pop();
        if(!previousScores.isEmpty())
            score = previousScores.pop();
    }

    public void randomMove() {
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                up();
                break;
            case 1:
                down();
                break;
            case 2:
                right();
                break;
            case 3:
                left();
                break;
        }
    }

    public boolean hasBoardChanged() {
       return getWeightTiles(gameTiles) != getWeightTiles(previousStates.peek());
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency;

        if (hasBoardChanged()) {
            rollback();
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(), score, move);
        } else {
            moveEfficiency = new MoveEfficiency(-1, 0, move);
        }

        return moveEfficiency;
    }

    private int getWeightTiles(Tile[][] tiles) {
        int result = 0;

        for (int n = 0; n < FIELD_WIDTH; n++) {
            for (int m = 0; m < FIELD_WIDTH; m++) {
                if (tiles[n][m].value >= 0)
                    result += tiles[n][m].value;
            }
        }

        return result;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue = new PriorityQueue<>(4, Collections.reverseOrder());

        priorityQueue.offer(new MoveEfficiency(getEmptyTiles().size(), score, new Move() {
            @Override
            public void move() {
                up();
            }
        }));
        priorityQueue.offer(new MoveEfficiency(getEmptyTiles().size(), score, new Move() {
            @Override
            public void move() {
                down();
            }
        }));
        priorityQueue.offer(new MoveEfficiency(getEmptyTiles().size(), score, new Move() {
            @Override
            public void move() {
                left();
            }
        }));
        priorityQueue.offer(new MoveEfficiency(getEmptyTiles().size(), score, new Move() {
            @Override
            public void move() {
                right();
            }
        }));

        MoveEfficiency moveEfficiency = priorityQueue.peek();
        moveEfficiency.getMove().move();
    }
}