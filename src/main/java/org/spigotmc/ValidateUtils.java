package org.spigotmc;

public class ValidateUtils {

    public static String limit(String str, int limit) {
        return str.length() > limit ? str.substring(0, limit) : str;
    }
}
