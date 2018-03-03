package com.javarush.task.task38.task3803;

/* 
Runtime исключения (unchecked exception)
*/

import java.util.ArrayList;
import java.util.Map;

public class VeryComplexClass {
    public void methodThrowsClassCastException() {
        Map map = (Map) new ArrayList();
    }

    public void methodThrowsNullPointerException() {
        String a = null;
        boolean i = a.equals("");
    }

    public static void main(String[] args) {

    }
}
