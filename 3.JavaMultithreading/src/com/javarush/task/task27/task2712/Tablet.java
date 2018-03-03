package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet{

    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Order createOrder() {
        Order order;
        try {
            order = new TestOrder(this);

            doOrder(order);

            return order;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

        return null;
    }


    public void createTestOrder() {
        Order order;
        try {
            order = new TestOrder(this);

            doOrder(order);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void doOrder(Order order) {
        if (!order.isEmpty()) {

            queue.add(order);
            // Показ реклами при приготовлении
            try {
                new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
            } catch (NoVideoAvailableException e) {
                logger.log(Level.INFO, "No video is available for the order " + order);
            }
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
