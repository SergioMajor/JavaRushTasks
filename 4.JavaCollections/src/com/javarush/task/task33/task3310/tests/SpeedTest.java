package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        long start = new Date().getTime();

        for (String string: strings)
            ids.add(shortener.getId(string));

        long finish = new Date().getTime();
        return finish - start;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        long start = new Date().getTime();

        for (Long id: ids)
            strings.add(shortener.getString(id));

        long finish = new Date().getTime();
        return finish - start;
    }

    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<Long> origIds = new HashSet<>();
        Set<String> origStrings = new HashSet<>();

        for (int i = 0; i < 10000; i++)
            origStrings.add(Helper.generateRandomString());

        origStrings.clear();
        long timeIds1 = getTimeForGettingIds(shortener1, origStrings, origIds);
        long timeIds2 = getTimeForGettingIds(shortener2, origStrings, origIds);

        Assert.assertTrue(timeIds1 > timeIds2);

        origStrings.clear();
        long timeString1 = getTimeForGettingStrings(shortener1, origIds, origStrings);
        long timeString2 = getTimeForGettingStrings(shortener2, origIds, origStrings);

        Assert.assertEquals(timeString1, timeString2, 30);
    }
}
