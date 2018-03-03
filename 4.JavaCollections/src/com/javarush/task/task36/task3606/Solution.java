package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplse"));
        System.out.println(solution.getHiddenClassObjectByKey("hiddenclassimplf"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        Loader loader = new Loader();
        File dir = new File(packageName);

        for (File file : dir.listFiles()) {
            Class clazz = loader.load(file.toPath());
            if (clazz != null) {
                if (HiddenClass.class.isAssignableFrom(clazz)) {
                    hiddenClasses.add(clazz);
                }
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        for (Class clazz : hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                try {
                    for (Constructor constructor : clazz.getDeclaredConstructors()) {
                        if (constructor.getParameterTypes().length == 0) {
                            constructor.setAccessible(true);
                            return (HiddenClass) constructor.newInstance();
                        }
                    }
                } catch (IllegalAccessException |
                        InstantiationException |
                        InvocationTargetException ignored) {
                    return null;
                }
            }
        }
        return null;
    }

    private class Loader extends ClassLoader {
        public Class<?> load(Path path) {
            if (path.toString().lastIndexOf(".class") == -1)
                return null;

            try {
                byte[] buffer = Files.readAllBytes(path);
                return defineClass(null, buffer, 0, buffer.length);
            } catch (IOException e) {
                return null;
            }
        }
    }
}