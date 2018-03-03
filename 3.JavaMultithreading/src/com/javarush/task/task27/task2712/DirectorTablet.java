package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String format = "%s - %.2f";
        String date;

        double total = 0;
        for (Map.Entry<Date, Double> iterator: StatisticManager.getInstance().getAdvertisementProfit().entrySet()) {
            // Форматируем дату
            date = dateFormat.format(iterator.getKey());
            total += iterator.getValue();

            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, format, date, iterator.getValue() / 100));
        }

        total /= 100;
        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, format, "Total", total));
    }

    public void printCookWorkloading() {
        Map<Date, Map<String, Integer>> statistic = StatisticManager.getInstance().getCookWorkloading();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String date;

        for (Map.Entry<Date, Map<String, Integer>> iterator: statistic.entrySet()) {
            // Форматируем дату
            date = dateFormat.format(iterator.getKey());

            ConsoleHelper.writeMessage(date);
            for (Map.Entry<String, Integer> innerIterator: iterator.getValue().entrySet()) {

                // Если повар не работал в какой-то из дней, то с пустыми данными его НЕ выводить
                if (innerIterator.getValue() > 0) {
                    int time = (int) Math.ceil(innerIterator.getValue() / 60.0d);
                    ConsoleHelper.writeMessage(innerIterator.getKey() + " - " + time + " min");
                }
            }
            ConsoleHelper.writeMessage("");
        }
    }

    public void printActiveVideoSet() {
        Map<String, Integer> statistic = new TreeMap<>(StatisticAdvertisementManager.getInstance().getActiveVideoSet());

//        ConsoleHelper.writeMessage("");
//        ConsoleHelper.writeMessage("Active videos:");

        for (Map.Entry<String, Integer> iterator: statistic.entrySet())
            ConsoleHelper.writeMessage(iterator.getKey() + " - " + iterator.getValue());

    }

    public void printArchivedVideoSet() {
        List<Advertisement> statistic = StatisticAdvertisementManager.getInstance().getAchivedVideoSet();

//        ConsoleHelper.writeMessage("");
//        ConsoleHelper.writeMessage("Archived videos:");
        Collections.sort(statistic, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement ad: statistic)
            ConsoleHelper.writeMessage(ad.getName());
    }


}
