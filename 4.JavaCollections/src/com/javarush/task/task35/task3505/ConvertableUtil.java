package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <T, K> Map<T, K> convert(List<? extends Convertable> list) {
        Map<T, K> result = new HashMap<>();

        for (Convertable user: list) {
            result.put((T) user.getKey(), (K) user);
        }

        return result;
    }
}
