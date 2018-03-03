package com.javarush.task.task27.task2712.ad;

import java.util.*;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();

    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    public Map<String, Integer> getActiveVideoSet() {
        Map<String, Integer> statistics = new HashMap<>();

        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() > 0)
                statistics.put(ad.getName(), ad.getHits());
        }

        return statistics;
    }
    public List<Advertisement> getAchivedVideoSet() {
        List<Advertisement> statistics = new ArrayList<>();

        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() == 0)
                statistics.add(ad);
        }

        return statistics;
    }

}
