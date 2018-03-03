package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;


public class Cook extends Observable implements Runnable {

    private String name;
    private boolean busy = false;
    private LinkedBlockingQueue<Order> queue;
    private LinkedBlockingQueue<Order> cooked;

    public Cook(String name) {
        this.name = name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void setCooked(LinkedBlockingQueue<Order> cooked) {
        this.cooked = cooked;
    }

    public void startCookingOrder(Order order) throws InterruptedException {
        busy = true;
        int min = order.getTotalCookingTime();

        ConsoleHelper.writeMessage(order.toString());
        ConsoleHelper.writeMessage("Start cooking - "
                + order
                + ", cooking time "
                + order.getTotalCookingTime()
                + "min");

        StatisticManager.getInstance().register(new CookedOrderEventDataRow( order.getTablet().getNumber() + "", toString(),
                min * 60,
                order.getDishes()));

        try {
            Thread.sleep(min * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Уведомляем официанта об приготовлении заказа
        setChanged();
        notifyObservers(order);
        cooked.put(order);
        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!queue.isEmpty()) {
                if (!isBusy()) {
                    try {
                        startCookingOrder(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
