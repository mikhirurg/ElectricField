package io.github.mikhirurg.physlab3;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Application {
    private static final ResourceBundle resourceBundle;
    private static final ResourceBundle resourceBundleUS;

    public enum Os {
        WIN, MAC, LINUX, OTHER
    }

    private static Os currentOs;

    static {
        ResourceBundle resourceBundleTmp;
        try {
            resourceBundleTmp = PropertyResourceBundle.getBundle("AppBundle");
        } catch (MissingResourceException e) {
            resourceBundleTmp = PropertyResourceBundle.getBundle("AppBundle", Locale.forLanguageTag("en"));
        }
        resourceBundle = resourceBundleTmp;
        resourceBundleUS = PropertyResourceBundle.getBundle("AppBundle", Locale.forLanguageTag("en"));
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static String getUSString(String key) {
        return resourceBundleUS.getString(key);
    }

    public static Os getOS() {
        if (currentOs == null) {
            String osStr = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((osStr.contains("mac")) || (osStr.contains("darwin"))) {
                currentOs = Os.MAC;
            } else if (osStr.contains("win")) {
                currentOs = Os.WIN;
            } else if (osStr.contains("nux")) {
                currentOs = Os.LINUX;
            } else {
                currentOs = Os.OTHER;
            }
        }
        return currentOs;
    }
}
