package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> iterator : denominations.entrySet()) {
            sum += iterator.getKey() * iterator.getValue();
        }
        return sum;
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectAmount) {
        return getTotalAmount() >= expectAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectAmount) throws NotEnoughMoneyException {
        TreeMap<Integer, Integer> copyDenomination = new TreeMap<>(Collections.reverseOrder());
        copyDenomination.putAll(denominations);

        Map<Integer, Integer> map;
        int initMoney;

        while (!copyDenomination.isEmpty()){
            map = new HashMap<>();
            initMoney = expectAmount;

            for (Map.Entry<Integer, Integer> iterator : copyDenomination.entrySet()) {
                if (initMoney >= iterator.getKey()) {
                    if (iterator.getValue() > 0) {
                        int count = initMoney / iterator.getKey();
                        if (count <= iterator.getValue()) {
                            initMoney -= iterator.getKey() * count;
                            map.put(iterator.getKey(), count);
                            copyDenomination.put(iterator.getKey(), copyDenomination.get(iterator.getKey()) - count);
                        } else {
                            map.put(iterator.getKey(), iterator.getValue());
                            initMoney -= iterator.getKey() * iterator.getValue();
                            copyDenomination.put(iterator.getKey(), 0);
                        }
                    }
                }
            }

            if (initMoney == 0) {
                denominations = new HashMap<>(copyDenomination);
                return map;
            }

            copyDenomination.remove(copyDenomination.firstKey());
        }
        
        throw new NotEnoughMoneyException();
    }
}
