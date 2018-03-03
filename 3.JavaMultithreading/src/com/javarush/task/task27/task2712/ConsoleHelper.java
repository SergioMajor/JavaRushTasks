package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bis.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new ArrayList<>();

        writeMessage("Веддите название блюд, exit для выхода: ");
        writeMessage(Dish.allDishesToString());

        String dishName;

        // Ввод блюда
        while (!(dishName = readString()).equals("exit")) {
            // Проверям, существует ли блюдо в меню
            Dish orderedDish = Dish.existDish(dishName);

            // Добавлям в список заказов блюдо если оно существует в меню
            if (orderedDish != null) {
                dishes.add(orderedDish);
            } else if (!dishName.equals("exit")) {
                writeMessage("Такого блюда нет в меню.");
            }
        }

        return dishes;
    }
}
