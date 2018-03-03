package com.javarush.task.task32.task3205;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHandler implements InvocationHandler {

    private SomeInterfaceWithMethods interfaceWithMethods;

    public CustomInvocationHandler(SomeInterfaceWithMethods interfaceWithMethods) {
        this.interfaceWithMethods = interfaceWithMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName() + " in");
        Object result = method.invoke(interfaceWithMethods, args);
        System.out.println(method.getName() + " out");

        return result;
    }
}
