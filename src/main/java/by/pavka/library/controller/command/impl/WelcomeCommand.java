package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.client.AppClient;
import by.pavka.library.model.service.InitService;
import by.pavka.library.model.service.impl.LibServiceFactory;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.util.ConstantManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * WelcomeCommand
 * <p>
 * This command is executed first and routs the application to a welcome page.
 *
 * @author Pavel Kassitchev
 * @vrsion 1.0
 */
public class WelcomeCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    InitService service = LibServiceFactory.getInitService();
    HttpSession session = request.getSession();
    PageRouter pageRouter = new PageRouter();
    try {
      int books = service.countBooks();
      int users = service.countUsers();
      ServletContext servletContext = request.getServletContext();
      servletContext.setAttribute(APP_ATTRIBUTE_BOOKS, books);
      servletContext.setAttribute(APP_ATTRIBUTE_USERS, users);
      if (session.isNew()) {
        AppClient client = new AppClient() {
          @Override
          public String getRole() {
            return ConstantManager.GUEST;
          }
        };
        session.setAttribute(SESSION_ATTRIBUTE_CLIENT, client);
      }
      pageRouter.setPage(PageRouter.WELCOME);
    } catch (ServiceException e) {
      pageRouter.setPage(PageRouter.ERROR);
      LOGGER.error("WelcomeCommand hasn't completed");
    }
    return pageRouter;
  }
}
