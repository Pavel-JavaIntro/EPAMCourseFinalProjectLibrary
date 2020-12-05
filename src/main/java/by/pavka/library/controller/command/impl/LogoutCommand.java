package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    return new PageRouter(PageRouter.INDEX);
  }

  @Override
  public PageRouter executeCommand(HttpServletRequest request) {
    HttpSession session = request.getSession();
    String page = (String)session.getAttribute(SESSION_ATTRIBUTE_PAGE);
    LOGGER.info("Current Page is " + page);
    PageRouter pageRouter = execute(request);
    String nextPage = pageRouter.getPage();
    LOGGER.info("Next Page is " + nextPage);
    session.invalidate();
    return pageRouter;
  }
}
