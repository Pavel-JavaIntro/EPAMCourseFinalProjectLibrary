package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SetLanguageCommand
 * <p>
 * This command is executed when a user changes the application language.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class SetLanguageCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    String language = request.getParameter(LANGUAGE);
    HttpSession session = request.getSession();
    session.setAttribute(SESSION_ATTRIBUTE_LANGUAGE, language);
    return new PageRouter();
  }
}
