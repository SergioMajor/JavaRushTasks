package com.javarush.task.task38.task3804;

public class FactoryException {

    public static Throwable get(Enum e) {
        if (e == null) return new IllegalArgumentException();

        String m = e.toString().replaceAll("_", " ");
        m = m.charAt(0) + m.substring(1).toLowerCase();

        if (e == ExceptionApplicationMessage.SOCKET_IS_CLOSED || e == ExceptionApplicationMessage.UNHANDLED_EXCEPTION)
            return new Exception(m);
        if (e == ExceptionDBMessage.NOT_ENOUGH_CONNECTIONS || e == ExceptionDBMessage.RESULT_HAS_NOT_GOTTEN_BECAUSE_OF_TIMEOUT)
            return new RuntimeException(m);
        if (e == ExceptionUserMessage.USER_DOES_NOT_EXIST || e == ExceptionUserMessage.USER_DOES_NOT_HAVE_PERMISSIONS)
            return new Error(m);

        return new IllegalArgumentException();
    }
}
