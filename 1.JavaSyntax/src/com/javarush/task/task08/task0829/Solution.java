package com.javarush.task.task08.task0829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
Модернизация ПО
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //list of addresses
        Map<String, String> addresses = new HashMap<>();
        String address;
        String family;

        while (true) {
            address = reader.readLine();
            if (address.isEmpty()) break;

            family = reader.readLine();
            if (family.isEmpty()) break;

            addresses.put(address, family);
        }

        address = reader.readLine();

        if (addresses.containsKey(address)) {
            String familySecondName = addresses.get(address);
            System.out.println(familySecondName);
        }
    }
}
