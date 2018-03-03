package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int sum;
        while (true) {
            try {
                sum = Integer.parseInt(ConsoleHelper.readString());
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                continue;
            }
            if (sum <= 0) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if (!currencyManipulator.isAmountAvailable(sum)) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            try {
                for (Map.Entry<Integer, Integer> pair : currencyManipulator.withdrawAmount(sum).entrySet())
                    ConsoleHelper.writeMessage("\t" + pair.getKey() + " - " + pair.getValue());
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            }
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, currencyCode));
            break;
        }
    }
}
