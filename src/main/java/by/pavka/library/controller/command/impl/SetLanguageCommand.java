package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SetLanguageCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    String language = request.getParameter(LANGUAGE);
    HttpSession session = request.getSession();
    session.setAttribute(SESSION_ATTRIBUTE_LANGUAGE, language);
    return new PageRouter();
  }
}
