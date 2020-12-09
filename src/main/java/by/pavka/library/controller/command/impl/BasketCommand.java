package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * BasketCommand
 * <p>
 * This command routs a reader to its basket where the books he was going to order are listed
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class BasketCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.removeAttribute(EDITIONS);
    return new PageRouter(PageRouter.ORDER);
  }
}
