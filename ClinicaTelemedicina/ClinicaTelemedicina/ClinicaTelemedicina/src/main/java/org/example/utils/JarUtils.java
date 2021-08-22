package org.example.utils;

public class JarUtils {
    public static boolean isRunningFromJar() {
        String classJar = JarUtils.class.getResource("jarUtils.class").toString();
        if (classJar.startsWith("jar:")) {
            return true;
        }
        return false;
    }
}
