package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserOptionsCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute(RESULT);
    session.removeAttribute(USERS);
    session.removeAttribute(USER_NAME);
    session.removeAttribute(USER_SURNAME);
    return new PageRouter(PageRouter.PROCESS_USERS);
  }
}
