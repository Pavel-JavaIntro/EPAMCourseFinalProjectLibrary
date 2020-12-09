package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * BookOptionsCommand
 * <p>
 * This command routs a librarian to the page from where he can find books by edtion id, add editions or books, decommission books.
 *
 * @author Pavel Kassitchev
 * version 1.0
 */
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
