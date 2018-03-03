package com.javarush.task.task27.task2712.kitchen;


import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order{

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        dishes = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int randomNumber = random.nextInt(10);
        int lenMenu = Dish.values().length;

        for (int i = 0; i < randomNumber; i++) {
            dishes.add(Dish.values()[random.nextInt(lenMenu)]);
        }
    }
}
