package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.Controller;
import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;

    // Объекты будут рисоваться на игровом поле Field
    private Field field;
    private EventListener eventListener;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        // Поле
        field = new Field(this);
        add(field);
        // Закритие окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Размер окна
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void setEventListener(EventListener eventListeners) {
        field.setEventListener(eventListeners);
    }

    public void update() {
        field.repaint();
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }

    public void completed(int level) {
        update();
        JOptionPane.showMessageDialog(this, "Level " + level + " completed!");
        controller.startNextLevel();
    }
}
