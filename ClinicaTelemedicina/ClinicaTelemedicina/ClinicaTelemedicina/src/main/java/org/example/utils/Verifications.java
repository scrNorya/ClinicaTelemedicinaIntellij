package org.example.utils;

public class Verifications {
    public static boolean isRunningFromJar() {
        String classJar = Verifications.class.getResource("Verifications.class").toString();
        if (classJar.startsWith("jar:")) {
            return true;
        }
        return false;
    }
}
