package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String s;

        try {
            s = bis.readLine();
        } catch (IOException ignored) {
            return null;
        }

        if (s.equalsIgnoreCase("EXIT")) {
            ConsoleHelper.writeMessage(res.getString("the.end"));
            throw new InterruptOperationException();
        }
        return s;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        String code;
        writeMessage(res.getString("choose.currency.code"));
        while (true) {
            if (!((code = readString()) == null || code.length() != 3)) break;
            writeMessage(res.getString("choose.currency.code"));
        }
        return code.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] money = new String[2];
        boolean isValid;

        do {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            isValid = true;
            String input = readString();

            if (input != null) {
                try {
                    money[0] = input.split("\\s")[0];
                    money[1] = input.split("\\s")[1];

                    int nominal = Integer.parseInt(money[0]);
                    int banknote = Integer.parseInt(money[1]);

                    if (nominal < 0 || banknote < 0) isValid = false;
                } catch (Exception ignore) {
                    isValid = false;
                }
            }
        }
        while (!isValid);

        return money;
    }

    public static Operation askOperation() throws InterruptOperationException {
        boolean isValid;
        Operation operation = null;

        do {
            isValid = true;

            writeMessage("");
            writeMessage(res.getString("choose.operation"));
            writeMessage("1 - " + res.getString("operation.INFO") + "\n" +
                    "2 - " + res.getString("operation.DEPOSIT") + "\n" +
                    "3 - " + res.getString("operation.WITHDRAW") + "\n" +
                    "4 - " + res.getString("operation.EXIT")
            );

            String input = readString();

            if ("EXIT".equalsIgnoreCase(input))
                throw new InterruptOperationException();

            try {
                if (input != null)
                    operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(input));
            } catch (IllegalArgumentException ignore) {
                isValid = false;
            }
        } while (!isValid);

        return operation;
    }

    public static void printExitMessage() {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }
}
