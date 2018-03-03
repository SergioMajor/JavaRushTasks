package com.javarush.task.task39.task3906;

/* 
Интерфейсы нас спасут!
*/
public class Solution {
    public static void main(String[] args) {
        Switchable switchable = new SecuritySystem();
        ElectricPowerSwitch electricPowerSwitch = new ElectricPowerSwitch(switchable);

        electricPowerSwitch.press();
        electricPowerSwitch.press();
    }
}
