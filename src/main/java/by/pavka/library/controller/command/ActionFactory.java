package by.pavka.library.controller.command;

import by.pavka.library.model.util.MessageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * ActionFactory
 * <p>
 * Extracts ActionCommands from the user's request parameters.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ActionFactory {
  public static ActionCommand defineCommand(HttpServletRequest request) {
    ActionCommand command = CommandEnum.EMPTY.getCommand();
    String action = request.getParameter(ActionCommand.COMMAND);
    if (action == null || action.isEmpty()) {
      return command;
    }
    try {
      CommandEnum commandEnum = CommandEnum.valueOf(action.toUpperCase());
      command = commandEnum.getCommand();
    } catch (IllegalArgumentException e) {
      String language = (String) request.getSession().getAttribute(ActionCommand.SESSION_ATTRIBUTE_LANGUAGE);
      Locale locale = language == null ? Locale.getDefault() : new Locale(language);
      request.setAttribute(
          ActionCommand.WRONG_ACTION,
          action + MessageManager.getProperty("message.wrongaction", locale));
    }
    return command;
  }
}
