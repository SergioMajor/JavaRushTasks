package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.FemaleFactory;
import com.javarush.task.task37.task3702.male.MaleFactory;

public class FactoryProducer {
    public static AbstractFactory getFactory(HumanFactoryType type) {
        switch (type) {
            case MALE:
                return new MaleFactory();
            default:
                return new FemaleFactory();
        }
    }

    public enum HumanFactoryType {
        MALE,
        FEMALE
    }
}