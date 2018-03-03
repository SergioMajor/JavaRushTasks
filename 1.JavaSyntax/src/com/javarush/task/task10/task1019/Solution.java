package com.javarush.task.task10.task1019;

import java.io.*;
import java.util.HashMap;

/* 
Функциональности маловато!
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> pairs = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String idValue;
        String name;

        while (!(idValue = reader.readLine()).isEmpty() && !(name = reader.readLine()).isEmpty()) {
            int id = Integer.parseInt(idValue);
            pairs.put(name, id);
        }

        for (HashMap.Entry<String, Integer> pair : pairs.entrySet()) {
            System.out.println(pair.getValue() + " " + pair.getKey());
        }
    }
}
