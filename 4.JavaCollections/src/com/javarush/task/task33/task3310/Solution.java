package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;
import com.javarush.task.task33.task3310.tests.FunctionalTest;
import com.javarush.task.task33.task3310.tests.SpeedTest;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
//        testStrategy(new HashMapStorageStrategy(), 10000);
//        testStrategy(new OurHashMapStorageStrategy(), 10000);
//        testStrategy(new OurHashBiMapStorageStrategy(), 10000);
//        testStrategy(new HashBiMapStorageStrategy(), 10000);
//        testStrategy(new DualHashBidiMapStorageStrategy(), 10000);
//        testStrategy(new FileStorageStrategy(), 100);
        FunctionalTest functionalTest = new FunctionalTest();
        functionalTest.testHashMapStorageStrategy();
        functionalTest.testOurHashMapStorageStrategy();
        functionalTest.testFileStorageStrategy();
        functionalTest.testDualHashBidiMapStorageStrategy();
        functionalTest.testOurHashBiMapStorageStrategy();

        SpeedTest speedTest = new SpeedTest();
        speedTest.testHashMapStorage();
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> list = new HashSet<>();
        for (String string : strings)
            list.add(shortener.getId(string));

        return list;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> list = new HashSet<>();
        for (Long key : keys)
            list.add(shortener.getString(key));

        return list;
    }

    public static void testStrategy(StorageStrategy storageStrategy, long elementsNumber) {
        Helper.printMessage(storageStrategy.getClass().getSimpleName());
        Set<String> randomStrings = new HashSet<>();

        for (long i = 0; i < elementsNumber; i++) {
            randomStrings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(storageStrategy);

        Date idStart = new Date();
        Set<Long> ids = getIds(shortener, randomStrings);
        Date idFinish = new Date();
        Helper.printMessage(String.valueOf(idFinish.getTime() - idStart.getTime()));

        Date stringStart = new Date();
        Set<String> strings = getStrings(shortener, ids);
        Date stringFinish = new Date();
        Helper.printMessage(String.valueOf(stringFinish.getTime() - stringStart.getTime()));

        if (randomStrings.size() == strings.size())
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");
    }
}
