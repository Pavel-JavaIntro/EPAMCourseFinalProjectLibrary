package by.pavka.library.model.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class provides access to messages.properties file
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class MessageManager {
  private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
  private static final String LOCALIZATION = "ru";

  private MessageManager() {
  }

  public static String getProperty(String key) {
    return resourceBundle.getString(key);
  }

  public static String getProperty(String key, Locale locale) {
    if (!locale.getLanguage().equalsIgnoreCase(LOCALIZATION)) {
      locale = new Locale("");
    }
    ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
    return bundle.getString(key);
  }
}
