package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/* 
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) {
        Set<Animal> animals = new HashSet<>();

        for (File file : new File(pathToAnimals).listFiles()) {
            if (file.getName().endsWith(".class")) {
                try {
                    ClassLoader loader = new ClassLoader() {
                        @Override
                        public Class<?> findClass(String name) throws ClassNotFoundException {
                            byte[] b = new byte[0];
                            try {
                                b = Files.readAllBytes(Paths.get(name));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return defineClass(null, b, 0, b.length);
                        }
                    };

                    Class clazz = loader.loadClass(file.toString());
                    if (Animal.class.isAssignableFrom(clazz)) {
                        for (Constructor constructor : clazz.getConstructors()) {
                            if (Modifier.PUBLIC == constructor.getModifiers() && constructor.getParameters().length == 0) {
                                animals.add((Animal) clazz.newInstance());
                            }
                        }
                    }
                } catch (IllegalAccessException | InstantiationException ignored) {
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return animals;
    }
}
