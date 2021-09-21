package org.example.utils;

import java.net.URL;
import java.util.Objects;

public class JarUtils {
    public static boolean isRunningFromJar() {
        URL resource = JarUtils.class.getResource("");
        if(resource != null) {
            String protocol = resource.getProtocol();
            return Objects.equals(protocol, "jar");
        } else {
            return false;
        }
    }
}
