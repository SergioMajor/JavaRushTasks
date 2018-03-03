package com.javarush.task.task34.task3410.model;

import java.io.*;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        if (level < 1) return null;
        if (level > 60) level %= 60;

        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(levels.toString()));
            boolean isLevelFind = false;
            String line;

            while (reader.ready() && !isLevelFind) {
                line = reader.readLine();
                if (line.startsWith("Maze: " + level)) {
                    String fileOffset = reader.readLine();
                    String sizeX = reader.readLine();
                    String sizeY = reader.readLine();
                    String end = reader.readLine();
                    String lenght = reader.readLine();
                    String emptyLine = reader.readLine();

                    int lengthX = Integer.parseInt(sizeX.substring(8).trim());
                    int lengthY = Integer.parseInt(sizeY.substring(8).trim());

                    int x0 = Model.FIELD_CELL_SIZE / 2;
                    int y0 = Model.FIELD_CELL_SIZE / 2;

                    int x;
                    int y = y0;

                    for (int j = 0; j < lengthY; j++) {
                        x = x0;
                        line = reader.readLine();
                        for (int i = 0; i < lengthX; i++) {
                            switch (line.charAt(i)) {
                                case 'X':
                                    walls.add(new Wall(x, y));
                                    break;
                                case '*':
                                    boxes.add(new Box(x, y));
                                    break;
                                case '.':
                                    homes.add(new Home(x, y));
                                    break;
                                case '&':
                                    homes.add(new Home(x, y));
                                    boxes.add(new Box(x, y));
                                    break;
                                case '@':
                                    player = new Player(x, y);
                                    break;
                            }
                            x += Model.FIELD_CELL_SIZE;
                        }
                        y += Model.FIELD_CELL_SIZE;
                    }

                    isLevelFind = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GameObjects(walls, boxes, homes, player);
    }
}
