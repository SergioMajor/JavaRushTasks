package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class StatisticManager {

    private static StatisticManager ourInstance = new StatisticManager();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {

    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<Date, Double> getAdvertisementProfit() {
        Map<Date, Double> statistic = new TreeMap<>();

        // Достаем из хранилища наши данние по вибору видео
        List<EventDataRow> list = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);

        Double videoTime;
        Date currentDate;

        for (EventDataRow item : list) {
            currentDate = getDateByDay(item.getDate());
            // Прибиль от видео
            videoTime = ((double) ((VideoSelectedEventDataRow) item).getAmount());

            // Проверем дату
            if (statistic.containsKey(currentDate)) {
                // Суммируем прибиль конкретного дня
                videoTime += statistic.get(currentDate);
            }

            // Обновление данных
            statistic.put(currentDate, videoTime);
        }

        return statistic;
    }

    public Map<Date, Map<String, Integer>> getCookWorkloading() {
        // Главная статистика
        Map<Date, Map<String, Integer>> statistic = new TreeMap<>();
        // Статистика для поваров конкретного дня
        Map<String, Integer> cookStatistic;
        // Достаем из хранилища наши данние заказов
        List<EventDataRow> list = statisticStorage.getStorage().get(EventType.COOKED_ORDER);

        // Для временных проверок, действий
        Integer cookingTime;
        Date currentDate;

        for (EventDataRow item : list) {
            currentDate = getDateByDay(item.getDate());

            // Время приготовление заказа
            cookingTime = item.getTime();
            String cookName = ((CookedOrderEventDataRow) item).getCookName();

            // Проверем дату
            if (statistic.containsKey(currentDate)) {
                // Берем статистику опредиленого дня
                cookStatistic = statistic.get(currentDate);

                if (cookStatistic.containsKey(cookName)) {
                    // Сумируем время приготовление блюд для опредиленого повара
                    cookingTime += cookStatistic.get(cookName);
                }
            } else {
                // Создание статистики для опредиленого дня
                cookStatistic = new TreeMap<>();
            }

            cookStatistic.put(cookName, cookingTime);

            // Обновление данных
            statistic.put(currentDate, cookStatistic);
        }

        return statistic;
    }

    public Date getDateByDay(Date date) {

        // Инициализируем день
        return new Date(date.getYear(), date.getMonth(), date.getDate());
    }


    private class StatisticStorage {
        Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        {
            for (EventType eventType: EventType.values())
                storage.put(eventType, new ArrayList<EventDataRow>());
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }
}
