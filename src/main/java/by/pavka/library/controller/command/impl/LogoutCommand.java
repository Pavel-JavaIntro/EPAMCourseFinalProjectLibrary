package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LogoutCommand
 * <p>
 * This command is executed when the user wants to logout. It returns the user to welcome page
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class LogoutCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    return new PageRouter(PageRouter.INDEX);
  }

  /**
   * Overrides the basing default method
   * @param request HttpServletRequest
   * @return PageRouter
   */
  @Override
  public PageRouter executeCommand(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String page = (String) session.getAttribute(SESSION_ATTRIBUTE_PAGE);
    LOGGER.info("Current Page is " + page);
    PageRouter pageRouter = execute(request);
    String nextPage = pageRouter.getPage();
    LOGGER.info("Next Page is " + nextPage);
    session.invalidate();
    return pageRouter;
  }
}
