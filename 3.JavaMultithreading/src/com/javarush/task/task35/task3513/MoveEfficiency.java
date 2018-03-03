package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency>{

    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public int compareTo(MoveEfficiency o) {
        int compareTiles = Integer.compare(numberOfEmptyTiles, o.numberOfEmptyTiles);
        if (compareTiles != 0)
            return compareTiles;

        int compareScore = Integer.compare(score, o.score);
        if (compareScore != 0)
            return compareScore;

        return 0;
    }

}
