package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;

    private final static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private final static LinkedBlockingQueue<Order> cookedQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Cook cookAmigo = new Cook("Amigo");
        Cook cookSergio = new Cook("Sergio");

        cookAmigo.setQueue(orderQueue);
        cookSergio.setQueue(orderQueue);

        cookSergio.setCooked(cookedQueue);
        cookSergio.setCooked(cookedQueue);

        Thread threadCookAmigo = new Thread(cookAmigo);
        Thread threadCookSergio = new Thread(cookSergio);

        threadCookAmigo.setDaemon(true);
        threadCookSergio.setDaemon(true);

        threadCookAmigo.start();
        threadCookSergio.start();



        Waiter waiter = new Waiter();
        Thread threadWaiter = new Thread(waiter);
        threadWaiter.setDaemon(true);
        waiter.setCooked(cookedQueue);
        threadWaiter.start();

        // Слушатель для планшета - менеджер заказов
        // Посетитель отправляет заказ повару
        // Повар начинает готовить
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        thread.interrupt();

//        DirectorTablet directorTablet = new DirectorTablet();
//
//        // Вивод статисики
//        directorTablet.printAdvertisementProfit();
//        directorTablet.printCookWorkloading();
//        directorTablet.printActiveVideoSet();
//        directorTablet.printArchivedVideoSet();
    }
}

