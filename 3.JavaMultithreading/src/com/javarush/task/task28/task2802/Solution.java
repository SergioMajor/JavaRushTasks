package com.javarush.task.task28.task2802;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* 
Пишем свою ThreadFactory
*/
public class Solution {

    public static void main(String[] args) {
        class EmulateThreadFactoryTask implements Runnable {
            @Override
            public void run() {
                emulateThreadFactory();
            }
        }

        ThreadGroup group = new ThreadGroup("firstGroup");
        Thread thread = new Thread(group, new EmulateThreadFactoryTask());

        ThreadGroup group2 = new ThreadGroup("secondGroup");
        Thread thread2 = new Thread(group2, new EmulateThreadFactoryTask());


        thread.start();
        thread2.start();
    }

    private static void emulateThreadFactory() {
        AmigoThreadFactory factory = new AmigoThreadFactory();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        factory.newThread(r).start();
        factory.newThread(r).start();
        factory.newThread(r).start();
    }

    public static class AmigoThreadFactory implements ThreadFactory {
        private AtomicInteger threadNumber = new AtomicInteger(1);
        private AtomicInteger poolNumber = new AtomicInteger(1);
        private static AtomicInteger tmp = new AtomicInteger(1);

        public AmigoThreadFactory() {
            poolNumber.set(tmp.getAndIncrement());
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread current = new Thread(r);

            if (current.isDaemon())
                current.setDaemon(false);
            if (current.getPriority() != Thread.NORM_PRIORITY)
                current.setPriority(Thread.NORM_PRIORITY);

            String threadName = getNameThread(current);
            current.setName(threadName);
            return current;
        }

        private String getNameThread(Thread t) {
            return t.getThreadGroup().getName()
                    + "-pool-" + poolNumber
                    + "-thread-" + threadNumber.getAndIncrement();
        }
    }
}
