package by.pavka.library.controller.command.impl;

import by.pavka.library.controller.command.ActionCommand;
import by.pavka.library.controller.command.PageRouter;
import by.pavka.library.entity.impl.User;
import by.pavka.library.model.service.LibraryService;
import by.pavka.library.model.service.ProcessUserService;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.LibServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * FindUsersCommand
 * <p>
 * This command is executed when a librarian inputs a surnme and a name of a user either to add a new one or
 * to change a status / role of an existing one.
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class FindUsersCommand implements ActionCommand {
  @Override
  public PageRouter execute(HttpServletRequest request) {
    PageRouter pageRouter = new PageRouter(PageRouter.PROCESS_USERS);
    HttpSession session = request.getSession();
    String surname = request.getParameter(USER_SURNAME);
    String name = request.getParameter(USER_NAME);
    if (surname != null && name != null) {
      session.removeAttribute(USERS);
      session.removeAttribute(RESULT);
      ProcessUserService service = LibServiceFactory.getProcessUserService();
      try {
        List<User> users = service.findUsers(surname, name);
        session.setAttribute(USER_SURNAME, surname);
        session.setAttribute(USER_NAME, name);
        if (!users.isEmpty()) {
          session.setAttribute(USERS, users);
        }
        session.setAttribute(USER_ADDITION, PageRouter.USER_ADDITION);
      } catch (ServiceException e) {
        pageRouter.setPage(PageRouter.ERROR);
        LOGGER.error("FindUsersCommand hasn't completed");
      }
    }
    return pageRouter;
  }
}
