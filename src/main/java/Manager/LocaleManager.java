package Manager;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class LocaleManager {
    private static String[] locale = {"ru", "RU"};

    public static String localizer(String message) throws UnsupportedEncodingException {
        java.util.Locale current = new java.util.Locale(locale[0], locale[1]);
        ResourceBundle rb = ResourceBundle.getBundle("gui", current);
        return rb.getString(message);
    }

    public static void setLanguage(String language, String country){
        locale[0] = language;
        locale[1] = country;
    }

    public static String[] getLocale() {
        return locale;
    }
}
