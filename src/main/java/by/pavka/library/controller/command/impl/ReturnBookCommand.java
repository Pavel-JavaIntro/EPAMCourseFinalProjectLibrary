package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ReturnBookCommand
 * <p>
 * This command routs a librarian to a page from where he or she can process a reader's book return.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class ReturnBookCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute(RESULT);
    session.removeAttribute(RETURNING);
    return new PageRouter(PageRouter.RETURN_BOOKS);
  }
}
