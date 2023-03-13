package io.github.mikhirurg.physlab3;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Application {
    private static final ResourceBundle resourceBundle;
    private static final ResourceBundle resourceBundleUS;

    static {
        resourceBundle = PropertyResourceBundle.getBundle("AppBundle");
        resourceBundleUS = PropertyResourceBundle.getBundle("AppBundle", Locale.forLanguageTag("en-us"));
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static String getUSString(String key) {
        return resourceBundleUS.getString(key);
    }

}
