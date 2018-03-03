package com.javarush.task.task39.task3906;

public class SecuritySystem implements Switchable {
    private boolean on = false;
    private Switchable lightBulb = new LightBulb();

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOff() {
        System.out.println("SecuritySystem is turning off!");
        on = false;
        lightBulb.turnOn();
    }

    @Override
    public void turnOn() {
        System.out.println("SecuritySystem is turning on!");
        on = true;
        lightBulb.turnOff();
    }
}
