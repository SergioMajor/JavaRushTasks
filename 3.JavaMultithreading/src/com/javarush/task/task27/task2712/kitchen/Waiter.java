package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.concurrent.LinkedBlockingQueue;

public class Waiter implements Runnable {
    private LinkedBlockingQueue<Order> cooked;

    public void setCooked(LinkedBlockingQueue<Order> cooked) {
        this.cooked = cooked;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!cooked.isEmpty()) {
                ConsoleHelper.writeMessage(cooked.poll() + " was assigned by waiter." );
            }
        }
    }
}
