package com.javarush.task.task27.task2712.ad;

public class Advertisement {

    protected Object content;
    private String name;
    private long initialAmount;
    protected int hits;
    protected int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;

        amountPerOneDisplaying = hits > 0 ? initialAmount / hits : 0;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() {
        if (hits > 0) hits--;
        else throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "name='" + name + '\'' +
                ", initialAmount=" + initialAmount +
                ", amountPerOneDisplaying=" + amountPerOneDisplaying +
                '}';
    }

    public int getHits() {
        return hits;
    }
}
