package com.javarush.task.task35.task3501;

public class GenericStatic{
    public static <S> S someStaticMethod(S genericObject) {
        System.out.println(genericObject);
        return genericObject;
    }
}
