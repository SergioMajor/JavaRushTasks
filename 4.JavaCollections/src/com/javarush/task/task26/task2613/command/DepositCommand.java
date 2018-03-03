package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        String currencyCode = ConsoleHelper.askCurrencyCode();
        String[] nominalBanknotes = ConsoleHelper.getValidTwoDigits(currencyCode);

        try {
            int nominal = Integer.parseInt(nominalBanknotes[0]);
            int banknote = Integer.parseInt(nominalBanknotes[1]);
            CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
            currencyManipulator.addAmount(nominal, banknote);
            ConsoleHelper.writeMessage(res.getString("success.format"));
        } catch (NumberFormatException ignore) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
    }
}
