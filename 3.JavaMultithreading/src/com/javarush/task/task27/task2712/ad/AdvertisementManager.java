package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {

    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        List<Advertisement> storeList = storage.list();

        if (storeList == null || storeList.isEmpty()) throw new NoVideoAvailableException();

        List<Advertisement> advertisements = new ArrayList<>();

        // Отбираем те видео в списке, в которих
        advertisements = findOptimalVideoList(advertisements);

        if (advertisements == null || advertisements.isEmpty()) {
            StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        } else {
            VideoSelectedEventDataRow videoSelectedEventDataRow = new VideoSelectedEventDataRow(advertisements, getSumAmount(new HashSet<>(advertisements)), getSumTime(new HashSet<>(advertisements)));
            StatisticManager.getInstance().register(videoSelectedEventDataRow);
        }

        showVideos(advertisements);
    }

    private List<Advertisement> findOptimalVideoList(List<Advertisement> advertisements) {

        Set<Set<Advertisement>> listsVarToDisplaying = getCombinations(new HashSet<>(advertisements));

        Set<Advertisement> optimalList = new HashSet<>();

        long maxSum = 0;
        //выбираем наборы с максимальной стоимостью
        Set<Set<Advertisement>> maxSumSets = new HashSet<>();
        for (Set<Advertisement> s : listsVarToDisplaying) {
            long currentSum = getSumAmount(s);
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }
        for (Set<Advertisement> s : listsVarToDisplaying) {
            if (getSumAmount(s) == maxSum) {
                maxSumSets.add(s);
            }
        }

        int maxTime = 0;
        int minCount = 0;
        Set<Set<Advertisement>> maxTimeSets = new HashSet<>();
        if (maxSumSets.size() > 1) {
            for (Set<Advertisement> s : maxSumSets) {
                int currentTime = getSumTime(s);
                if (currentTime > maxTime) {
                    maxTime = currentTime;
                }
            }
            for (Set<Advertisement> s : maxSumSets) {
                if (getSumTime(s) == maxTime) {
                    maxTimeSets.add(s);
                }
            }

            if (maxTimeSets.size() > 1) {
                for (Set<Advertisement> s : maxTimeSets) {
                    if (minCount == 0) {
                        minCount = s.size();
                        optimalList = s;
                    } else {
                        if (s.size() < minCount) {
                            minCount = s.size();
                            optimalList = s;
                        }
                    }
                }
            } else {
                optimalList = maxTimeSets.iterator().next();
            }
        } else {
            optimalList = maxSumSets.iterator().next();
        }


        List<Advertisement> showList = new ArrayList<>(optimalList);
        showList.sort((o1, o2) -> {
            if ((o1.getAmountPerOneDisplaying() - o2.getAmountPerOneDisplaying()) == 0) {
                return (int) (o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration() - o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration());
            } else {
                return (int) (o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
            }
        });

        return showList;
    }

    public Set<Set<Advertisement>> getCombinations(Set<Advertisement> list) {
        boolean isEnd = true;
        Set<Set<Advertisement>> result = new HashSet<>();

        for (Advertisement ad : storage.list()) {
            if (isAvailableBranch(list, ad)) {
                Set<Advertisement> newCopyList = copyList(list);
                newCopyList.add(ad);
                result.addAll(getCombinations(newCopyList));
                isEnd = false;
            }
        }
        if (isEnd) {
            result.add(list);
        }
        return result;
    }


    public boolean isAvailableBranch(Set<Advertisement> set, Advertisement ad) {
        return ((getSumTime(set) + ad.getDuration() <= timeSeconds) && !set.contains(ad) && ad.getHits() > 0);
    }

    public Set<Advertisement> copyList(Set<Advertisement> l) {
        Set<Advertisement> copyList = new HashSet<>();
        copyList.addAll(l);
        return copyList;
    }

    private int getSumTime(Set<Advertisement> list) {
        int sum = 0;
        for (Advertisement ad : list) {
            sum += ad.getDuration();
        }

        return sum;
    }

    private long getSumAmount(Set<Advertisement> list) {
        long sum = 0;
        for (Advertisement ad : list) {
            sum += ad.getAmountPerOneDisplaying();
        }

        return sum;
    }

    private void showVideos(List<Advertisement> list) {

        for (Advertisement ad : list) {
            ConsoleHelper.writeMessage(ad.getName()
                    + " is displaying... "
                    + ad.getAmountPerOneDisplaying()
                    + ", " + ad.getAmountPerOneDisplaying() * 1000 / ad.getDuration());

            // Вичитиваем количество еще оставшихся оплачеваемих просмотров реклами
            ad.revalidate();
        }
    }
}