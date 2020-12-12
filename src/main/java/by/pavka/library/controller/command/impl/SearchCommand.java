package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SearchCommand
 * <p>
 * This command routs a user to a page from where he or she can search books by title and / or authors.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class SearchCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute(EDITIONS);
    return new PageRouter(PageRouter.SEARCH);
  }
}
