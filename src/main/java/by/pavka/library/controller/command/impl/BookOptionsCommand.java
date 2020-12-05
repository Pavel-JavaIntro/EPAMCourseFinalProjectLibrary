package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BookOptionsCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute(ADDITION);
    session.removeAttribute(DECOMMISSION);
    session.removeAttribute(RESULT);
    return new PageRouter(PageRouter.PROCESS_BOOKS);
  }
}
