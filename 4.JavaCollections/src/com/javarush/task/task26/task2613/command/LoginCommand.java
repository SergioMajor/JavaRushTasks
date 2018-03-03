package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        String pin;
        String password;

        do {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            pin = ConsoleHelper.readString();
            password = ConsoleHelper.readString();

            if ((pin != null ? pin.length() : 0) == 12 && (password != null ? password.length() : 0) == 4) {
                if (isNumber(pin) && isNumber(password)) {
                    if (validCreditCards.containsKey(pin) && validCreditCards.getString(pin).equals(password)) {
                        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), pin));
                        break;
                    } else {
                        ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    }
                } else {
                    ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                }
            } else {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }

        } while (true);

        ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), pin));
    }

    private boolean isNumber(String string) {
        for (char c :string.toCharArray()) if (!Character.isDigit(c)) return false;
        return true;
    }
}
