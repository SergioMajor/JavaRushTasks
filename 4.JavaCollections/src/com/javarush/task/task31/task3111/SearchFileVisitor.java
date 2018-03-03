package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName;
    private String partOfContent;
    private int maxSize = -1;
    private int minSize = -1;
    private List<Path> foundFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if ((minSize == -1 || Files.size(file) >= minSize) && (maxSize == -1 || Files.size(file) <= maxSize)) {
            if (partOfName == null || file.getFileName().toString().contains(partOfName)) {
                if (partOfContent == null) {
                    addFoundFiles(file);
                } else {
                    String string = new String(Files.readAllBytes(file));
                    if (string.contains(partOfContent)) {
                       addFoundFiles(file);
                    }
                }
            }
        }

        return FileVisitResult.CONTINUE;
    }

    private void addFoundFiles(Path file) {
        foundFiles.add(file);
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }
}
